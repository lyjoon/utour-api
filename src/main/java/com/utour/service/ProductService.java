package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.controller.ProductController;
import com.utour.dto.PagingResultDto;
import com.utour.dto.product.*;
import com.utour.dto.view.ViewComponentAccommodationDto;
import com.utour.dto.view.ViewComponentDto;
import com.utour.dto.view.ViewComponentEditorDto;
import com.utour.entity.*;
import com.utour.exception.InternalException;
import com.utour.mapper.*;
import com.utour.util.ErrorUtils;
import com.utour.util.FileUtils;
import com.utour.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends CommonService {

    private final ProductMapper productMapper;
    private final ProductImageGroupMapper productImageGroupMapper;
    private final ProductImageMapper productImageMapper;
    private final CommerceMapper commerceMapper;

    private final ViewComponentAccommodationMapper viewComponentAccommodationMapper;
    private final ViewComponentEditorMapper viewComponentEditorMapper;
    private final ViewComponentMapper viewComponentMapper;

    @Value(value = "${app.file-upload-storage.temp:}")
    private Path tempPath;

    @Value(value = "${app.file-upload-storage.content:}")
    private Path contentPath;

    @Value(value = "${app.file-upload-storage.product:}")
    private Path productPath;

    private final CodeService codeService;


    /**
     * 페이징 조회
     * @param productPagingDto
     * @return
     */
    public PagingResultDto getPageList(ProductQueryDto productPagingDto) {
        List<ProductDto> result = this.productMapper.findPage(productPagingDto)
                .stream()
                .map(vo -> {
                    ProductDto productDto = this.convert(vo, ProductDto.class);
                    if(StringUtils.hasText(productDto.getNationCode())) {
                        Optional.ofNullable(codeService.getNation(productDto.getNationCode())).ifPresent(nationDto -> productDto.setNationName(nationDto.getNationName()));
                    }

                    return productDto;
                })
                .collect(Collectors.toList());

        Long count = this.productMapper.count(productPagingDto);

        return PagingResultDto.builder()
                .page(productPagingDto.getPage())
                .limit(productPagingDto.getLimit())
                .count(count)
                .result(result)
                .build();
    }

    /**
     * 상품상세조회
     * @param productId
     * @return
     */
    public ProductViewDto get(Long productId) {
        return Optional.ofNullable(this.productMapper.findById(Product.builder().productId(productId).build()))
                .map(product -> this.convert(product, ProductDto.class))
                .map(productDto -> {
                    // 상품 이미지 조회하기
                    List<ProductImageGroupDto> productImageGroupList =
                            this.productImageGroupMapper.findAll(ProductImageGroup.builder().productId(productDto.getProductId()).build()).stream()
                                    .map(productImageGroup -> {
                                        ProductImageGroupDto productImageGroupDto = this.convert(productImageGroup, ProductImageGroupDto.class);

                                        List<ProductImageDto> productImages = this.productImageMapper.findAll(ProductImage.builder()
                                                        .productId(productImageGroupDto.getProductId())
                                                        .productImageGroupId(productImageGroupDto.getProductImageGroupId())
                                                        .build()
                                                )
                                                .stream()
                                                .map(productImage -> this.convert(productImage, ProductImageDto.class))
                                                .collect(Collectors.toList());
                                        productImageGroupDto.setProductImages(productImages);
                                        return productImageGroupDto;
                                    })
                                    .collect(Collectors.toList());

                    // viewComponents 조회하기
                    Map<Constants.ViewComponentType, ViewComponentDto> viewComponentMap = new HashMap<>();
                    for(ViewComponent viewComponent : this.viewComponentMapper.findAll(ViewComponent.builder().productId(productId).build())) {
                        Optional.ofNullable(Arrays.stream(Constants.ViewComponentType.values()).filter(v -> v.name().equals(viewComponent.getViewComponentType())).findFirst().orElse(null))
                                .ifPresent(viewComponentType -> {
                                    switch (viewComponentType) {
                                        case EDITOR:
                                            Optional.ofNullable(this.viewComponentEditorMapper.findById(ViewComponentEditor.builder()
                                                    .viewComponentId(viewComponent.getViewComponentId())
                                                    .build())).ifPresent(viewComponentEditor -> viewComponentMap.put(viewComponentType, this.convert(viewComponentEditor, ViewComponentEditorDto.class)));
                                            break;
                                        case ACCOMMODATION:
                                            Optional.ofNullable(this.viewComponentAccommodationMapper.findById(
                                                    ViewComponentAccommodation.builder().viewComponentId(viewComponent.getViewComponentId()).build()))
                                                    .ifPresent(viewComponentAccommodation -> viewComponentMap.put(viewComponentType, this.convert(viewComponentAccommodation, ViewComponentAccommodationDto.class)));
                                            break;
                                    }
                                });
                    }

                    return ProductViewDto.builder()
                            .productDto(productDto)
                            .productImageGroups(productImageGroupList)
                            .viewComponents(viewComponentMap)
                            .build();
                })
                .orElse(null);
    }

    /**
     * 여행상품 신규저장(insert)
     * @param productStoreDto
     * @param repProductImageFile
     * @param productImageFiles
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void insert(ProductStoreDto productStoreDto, MultipartFile repProductImageFile, MultipartFile[] productImageFiles) throws InternalException {

        ProductDto productDto = productStoreDto.getProductDto();
        LocalDate currentDate = LocalDate.now(); // 등록일자 : 오늘

        // 상품 기본개요를 저장한다.(productId 값을 반환받아야 함)
        Product.ProductBuilder productBuilder = Product.builder()
                .areaCode(productDto.getAreaCode())
                .nationCode(productDto.getNationCode())
                .content(productDto.getContent())
                .productType(productDto.getProductType())
                .title(productDto.getTitle())
                .useYn(Optional.ofNullable(productDto.getUseYn()).orElse(Constants.Y));

        // 대표 이미지가 있는 경우, content-path 에 저장시킨 뒤 product.rep_image_src 값을 외부에서 접근가능한 url 로 지정해준다.
        Optional.ofNullable(repProductImageFile).ifPresent(multipartFile -> {
            try {
                Path repImagePath = FileUtils.uploadFile(productPath, currentDate, repProductImageFile);
                java.io.File file = repImagePath.toFile();
                // productBuilder.repImageSrc(this.contextPath + ProductController.PRODUCT_IMAGE_LINK_URL);
                productBuilder.repImagePath(file.getPath());
            } catch (IOException e) {
                log.warn("{}", ErrorUtils.throwableInfo(e));
            }
        });

        Product product = productBuilder.build();

        // 신규저장
        this.productMapper.save(product);

        // 이미지 그룹을 저장한다.(신규저장이니 기존데이터 고려할 것이 없음)
        // 이미지 그룹 내 등록된 이미지 파일명과 전달받은 상품 이미지 목록(productImageFiles)이 일치하는 경우 저장함.
        Optional.ofNullable(productStoreDto.getProductImageGroupList()).ifPresent(productImageGroupList -> {
            // 이미지 그룹 저장 순환문
            AtomicInteger atomicInteger = new AtomicInteger(0);
            for(ProductImageGroupDto productImageGroupDto : productImageGroupList) {
                ProductImageGroup productImageGroup = ProductImageGroup.builder()
                        .productId(product.getProductId())
                        .groupName(productImageGroupDto.getGroupName())
                        .productImageGroupId(null)
                        .useYn(Optional.ofNullable(productImageGroupDto.getUseYn()).orElse(Constants.Y))
                        .build();

                this.productImageGroupMapper.save(productImageGroup);

                // 상품 이미지 데이터가 있는 경우 -> 'productImageFiles' 와 대조해서 저장함
                if(!Optional.ofNullable(productImageGroupDto.getProductImages()).map(List::isEmpty).orElse(true)) {
                    // 이미지 그룹 내 이미지 저장
                    Optional.ofNullable(productImageFiles).ifPresent(multipartFiles -> {
                        for(ProductImageDto productImageDto : productImageGroupDto.getProductImages()) {
                            String originFileName = productImageDto.getOriginFileName();
                            for(MultipartFile multipartFile : multipartFiles) {
                                if(originFileName.startsWith(atomicInteger.get() + "$") && originFileName.contains(multipartFile.getOriginalFilename())) {
                                    // 매칭되는 파일확인 -> productPath 경로에 저장 후 데이터를 테이블에 저장.
                                    try {
                                        Path path = FileUtils.uploadFile(productPath, currentDate, multipartFile);

                                        ProductImage productImage = ProductImage.builder()
                                                .productId(product.getProductId())
                                                .imagePath(path.toFile().getPath())
                                                .productImageGroupId(productImageGroup.getProductImageGroupId())
                                                .title(productImageDto.getTitle())
                                                .description(productImageDto.getDescription())
                                                .build();

                                        this.productImageMapper.save(productImage);
                                    } catch (IOException ioException) {
                                        log.error("{}", ErrorUtils.throwableInfo(ioException));
                                    } finally {
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
                atomicInteger.incrementAndGet();
            }
        });

        // 화면구성요소 목록을 저장함.
        // editor 유형인 경우 tempPath 경로로 첨부된 이미지 파일이 있는 경우 => 이미지 파일 저장경로로 이관 뒤 경로를 수정(replace)해야함
        if(!Optional.ofNullable(productStoreDto.getViewComponents()).map(Map::isEmpty).orElse(true)) {
            Function<Supplier<ViewComponent>, Long> function = viewComponentSupplier -> {
                ViewComponent viewComponent = viewComponentSupplier.get();
                this.viewComponentMapper.save(viewComponent);
                return viewComponent.getViewComponentId();
            };

            AtomicInteger atomicInteger = new AtomicInteger(1);

            Iterator<Map.Entry<String, Map<String, Object>>> iterator = productStoreDto.getViewComponents().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map<String, Object>> entry = iterator.next();

                // 상호 정의된 Component 유형이 아닌경우 버림.
                Constants.ViewComponentType viewComponentType = Arrays.stream(Constants.ViewComponentType.values())
                        .filter(type -> type.name().equals(entry.getKey())).findFirst().orElse(null);

                if (Objects.isNull(viewComponentType)) continue;

                this.saveViewComponent(product.getProductId(), viewComponentType, entry.getValue(), atomicInteger.get());

                atomicInteger.incrementAndGet();
            }
        }
    }


    /**
     * 상품정보 업데이트
     * @param productStoreDto
     * @param repProductImageFile
     * @param productImageFiles
     * @throws InternalException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(ProductStoreDto productStoreDto, MultipartFile repProductImageFile, MultipartFile[] productImageFiles) throws InternalException, IOException {
        ProductDto productDto = productStoreDto.getProductDto();

        valid : {

            // 상품을 조회함
            Product findProduct = this.productMapper.findById(Product.builder()
                    .productId(productDto.getProductId())
                    .build());

            if(Objects.isNull(findProduct)) break valid;

            Product.ProductBuilder productBuilder = Product.builder()
                    .productId(findProduct.getProductId())
                    .areaCode(StringUtils.defaultString(productDto.getAreaCode(), findProduct.getAreaCode()))
                    .nationCode(StringUtils.defaultString(productDto.getNationCode(), findProduct.getNationCode()))
                    .content(StringUtils.defaultString(productDto.getContent(), findProduct.getContent()))
                    .productType(StringUtils.defaultString(productDto.getProductType(), findProduct.getProductType()))
                    .title(StringUtils.defaultString(productDto.getTitle(), findProduct.getTitle()))
                    .useYn(Optional.ofNullable(productDto.getUseYn()).orElse(findProduct.getUseYn()));

            // 첨부된 파일이 있는 경우 -> 기존 이미지 파일 삭제처리 이후 재등록함
            if(repProductImageFile != null && !repProductImageFile.isEmpty()) {
                Path path = StringUtils.hasText(findProduct.getRepImagePath()) ? Paths.get(findProduct.getRepImagePath()) : null;
                if(path != null) {
                    FileUtils.delete(path);
                    Path uploadedFilePath = FileUtils.uploadFile(productPath, repProductImageFile);
                    java.io.File file = uploadedFilePath.toFile();
                    productBuilder.repImagePath(file.getPath());
                }
            }

            Product product = productBuilder.build();

            // 저장
            this.productMapper.save(product);

            AtomicReference<ProductImageGroup> refProductImageGroup = new AtomicReference<>(null);
            AtomicInteger atomicInteger = new AtomicInteger(0);
            // 이미지 신규저장 function
            Consumer<ProductImageDto> createProductImageConsumer = productImageDto -> {
                Optional.ofNullable(productImageFiles).ifPresent(multipartFiles -> {
                    String originFileName = productImageDto.getOriginFileName();
                    for(MultipartFile multipartFile : multipartFiles) {
                        if(originFileName.startsWith(atomicInteger.get() + "$") && originFileName.contains(multipartFile.getOriginalFilename())) {
                            // 매칭되는 파일확인 -> productPath 경로에 저장 후 데이터를 테이블에 저장.
                            try {
                                Path path = FileUtils.uploadFile(productPath, multipartFile);
                                ProductImage productImage = ProductImage.builder()
                                        .productId(product.getProductId())
                                        .imagePath(path.toFile().getPath())
                                        .productImageGroupId(refProductImageGroup.get().getProductImageGroupId())
                                        .title(productImageDto.getTitle())
                                        .description(productImageDto.getDescription())
                                        .build();

                                this.productImageMapper.save(productImage);
                            } catch (IOException ioException) {
                                log.error("{}", ErrorUtils.throwableInfo(ioException));
                            } finally {
                                break;
                            }
                        }
                    }
                });
            };

            // 이미지 그룹저장
            Optional.ofNullable(productStoreDto.getProductImageGroupList()).ifPresent(productImageGroupList -> {
                for (ProductImageGroupDto productImageGroupDto : productImageGroupList) {

                    if(productImageGroupDto.getProductImageGroupId() != null) {
                        ProductImageGroup _productImageGroup = ProductImageGroup.builder().productId(product.getProductId())
                                .productImageGroupId(productImageGroupDto.getProductImageGroupId()).build();
                        boolean existsProductImageGroup = this.productImageGroupMapper.exists(_productImageGroup);

                        if(existsProductImageGroup) { // 기존데이터 존재유무
                            if(productImageGroupDto.isDeleteYn()) { // 삭제 유무(true 일경우 해당데이터 + 하위데이터 삭제함., false 인 경우 insert or update)
                                // 상품이미지 파일 & 상품이미지 entity & 상품 이미지 그룹 entity 삭제
                                this.productImageMapper.findAll(ProductImage.builder().productId(_productImageGroup.getProductId()).productImageGroupId(_productImageGroup.getProductImageGroupId()).build())
                                        .forEach(productImage -> {
                                            if(StringUtils.hasText(productImage.getImagePath())) {
                                                try {
                                                    FileUtils.delete(Paths.get(productImage.getImagePath()));
                                                } catch (IOException e) {
                                                    log.error("{}", ErrorUtils.throwableInfo(e));
                                                }
                                            }
                                            this.productImageMapper.delete(productImage);
                                        });
                                // 이미지 그룹정보 삭제
                                this.productImageGroupMapper.delete(_productImageGroup);
                                refProductImageGroup.set(null);
                                continue;
                            } else {
                                // 이미지 그룹정보 업데이트 (productImageGroupId 가 지정됨)
                                ProductImageGroup productImageGroup = ProductImageGroup.builder()
                                        .productImageGroupId(productImageGroupDto.getProductImageGroupId())
                                        .productId(product.getProductId())
                                        .groupName(productImageGroupDto.getGroupName())
                                        .useYn(Optional.ofNullable(productImageGroupDto.getUseYn()).orElse(Constants.Y))
                                        .build();

                                this.productImageGroupMapper.save(productImageGroup);
                                refProductImageGroup.set(productImageGroup);
                            }
                        } else {
                            // 이미지 그룹정보 신규저장 (productImageGroupId 가 지정되지 않음)
                            ProductImageGroup productImageGroup = ProductImageGroup.builder()
                                    //.productImageGroupId(productImageGroupDto.getProductImageGroupId())
                                    .productId(product.getProductId())
                                    .groupName(productImageGroupDto.getGroupName())
                                    .useYn(Optional.ofNullable(productImageGroupDto.getUseYn()).orElse(Constants.Y))
                                    .build();

                            this.productImageGroupMapper.save(productImageGroup);
                            refProductImageGroup.set(productImageGroup);
                        }
                    } else {
                        // 이미지 그룹정보 신규저장 (productImageGroupId 가 지정되지 않음)
                        ProductImageGroup productImageGroup = ProductImageGroup.builder()
                                //.productImageGroupId(productImageGroupDto.getProductImageGroupId())
                                .productId(product.getProductId())
                                .groupName(productImageGroupDto.getGroupName())
                                .useYn(Optional.ofNullable(productImageGroupDto.getUseYn()).orElse(Constants.Y))
                                .build();

                        this.productImageGroupMapper.save(productImageGroup);
                        refProductImageGroup.set(productImageGroup);
                    }

                    // 이미지 정보 저장
                    if(productImageGroupDto.getProductImages() != null && !productImageGroupDto.getProductImages().isEmpty()) {
                        for(ProductImageDto productImageDto : productImageGroupDto.getProductImages()) {

                            if(productImageDto.getProductImageId() != null && productImageDto.getProductImageGroupId() != null) {

                                // 이전에 저장된 상품 이미지
                                ProductImage productImage = this.productImageMapper.findById(ProductImage.builder()
                                        .productImageId(productImageDto.getProductImageId())
                                        .build());

                                // 기존 데이터가 확인되는 경우
                                if(productImage != null) {
                                    if(productImageDto.isDeleteYn()) {
                                        // 상품이미지 파일 및 정보삭제
                                        if(StringUtils.hasText(productImage.getImagePath())) {
                                            try {
                                                FileUtils.delete(Paths.get(productImage.getImagePath()));
                                            } catch (IOException e) {
                                                log.error("{}", ErrorUtils.throwableInfo(e));
                                            }
                                        }
                                        this.productImageMapper.delete(productImage);
                                    } else {
                                        // 이미지 정보 저장(update)
                                        // multipartFile 파일이름과 originFileName 이 매칭되는 데이터의 경우 -> 1. 기존이미지 검색 후 삭제처리, 2. 업데이트(저장)
                                        String originFileName = productImageDto.getOriginFileName();

                                        ProductImage.ProductImageBuilder productImageBuilder = ProductImage.builder()
                                                .productId(product.getProductId())
                                                .productImageGroupId(refProductImageGroup.get().getProductImageGroupId())
                                                .productImageId(productImage.getProductImageId())
                                                .description(productImageDto.getDescription())
                                                .title(productImageDto.getTitle())
                                                ;

                                        Arrays.stream(productImageFiles).filter(multipartFile -> multipartFile.getOriginalFilename().equals(originFileName))
                                                .findFirst()
                                                .ifPresent(multipartFile -> {
                                                    try {
                                                        Path uploadFilePath = FileUtils.uploadFile(this.productPath, multipartFile);
                                                        productImageBuilder.imagePath(uploadFilePath.toFile().getPath());
                                                    } catch (IOException e) {
                                                        log.error("{}", ErrorUtils.throwableInfo(e));
                                                    }
                                                });

                                        this.productImageMapper.save(productImageBuilder.build());
                                    }
                                } else {
                                    // 이전에 저장된 데이터가 없는경우 -> 상품 이미지 신규저장
                                    createProductImageConsumer.accept(productImageDto);
                                }

                            } else {
                                // 상품 이미지 신규저장
                                createProductImageConsumer.accept(productImageDto);
                            }
                        }
                    }

                } //for-each (product-image-group)
            }); // optional (product-image-group)


            AtomicInteger atomicInteger1 = new AtomicInteger(0);

            //viewComponents 저장
            Optional.ofNullable(productStoreDto.getViewComponents()).ifPresent(viewComponents -> {
                Iterator<Map.Entry<String, Map<String, Object>>> iterator = viewComponents.entrySet().iterator();
                while (iterator.hasNext()) {
                    atomicInteger1.incrementAndGet();
                    Map.Entry<String, Map<String, Object>> entry = iterator.next();
                    Long viewComponentId = Optional.ofNullable(entry.getValue().get("viewComponentId")).map(v -> (Long) v).orElse(null);
                    Constants.ViewComponentType viewComponentType = Arrays.stream(Constants.ViewComponentType.values()).filter(v -> v.name().equals(entry.getKey())).findFirst().orElse(null);

                    if(Objects.isNull(viewComponentType)) {
                        continue;
                    }

                    if(viewComponentId != null) {
                        boolean exists = this.viewComponentMapper.exists(ViewComponent.builder().viewComponentId(viewComponentId).build());
                        if(exists) {
                            // 업데이트
                            this.saveViewComponent(product.getProductId(), viewComponentType, entry.getValue(), atomicInteger1.get());
                        } else {
                            // 신규저장
                            if(entry.getValue().containsKey("viewComponentId")) {
                                entry.getValue().remove("viewComponentId");
                            }
                            this.saveViewComponent(product.getProductId(), viewComponentType, entry.getValue(), atomicInteger1.get());
                        }

                    } else {
                        // 신규저장
                        this.saveViewComponent(product.getProductId(), viewComponentType, entry.getValue(), atomicInteger1.get());
                    }
                }
            });

        } // valid.
    }

    /**
     * 상품 삭제
     * @param productId 상품 ID
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long productId) {

        // 등록된 상품이 유효한지 조회함
        Product product = this.productMapper.findById(Product.builder().productId(productId).build());
        valid : {
            if(Objects.isNull(product)) break valid;

            // 등록된 커머스 조회함 -> 삭제
            Commerce commerce = Commerce.builder().productId(productId).build();
            if(commerceMapper.exists(commerce)) {
                this.commerceMapper.delete(commerce);
            }

            // 구성요소를 조회함 -> 삭제
            Optional.ofNullable(this.viewComponentMapper.findAll(ViewComponent.builder().productId(productId).build()))
                    .ifPresent(viewComponents -> {
                        for(ViewComponent viewComponent : viewComponents) {
                            Constants.ViewComponentType viewComponentType = Arrays.stream(Constants.ViewComponentType.values())
                                    .filter(v -> v.name().equals(viewComponent.getViewComponentType()))
                                    .findFirst().orElse(null);

                            if(Objects.isNull(viewComponentType)) {
                                this.viewComponentMapper.delete(viewComponent);
                                continue;
                            } else {
                                switch (viewComponentType) {
                                    case EDITOR:
                                        // TODO : 첨부된 이미지 파일 삭제처리
                                        this.viewComponentEditorMapper.delete(ViewComponentEditor.builder().viewComponentId(viewComponent.getViewComponentId()).build());
                                        break;
                                    case ACCOMMODATION:
                                        this.viewComponentAccommodationMapper.delete(ViewComponentAccommodation.builder().viewComponentId(viewComponent.getViewComponentId()).build());
                                        break;
                                }
                                this.viewComponentMapper.delete(viewComponent);
                            }
                        }
                    });

            // 이미지 구성요소를 조회함 -> 삭제
            this.productImageGroupMapper.findAll(ProductImageGroup.builder().productId(productId).build())
                    .forEach(productImageGroup -> {
                        this.productImageMapper.findAll(ProductImage.builder()
                                        .productId(productImageGroup.getProductId())
                                        .productImageGroupId(productImageGroup.getProductImageGroupId())
                                        .build())
                                .forEach(productImage -> {
                                    try {
                                        FileUtils.delete(Paths.get(productImage.getImagePath()));
                                    } catch (IOException e) {
                                        log.error("{}", ErrorUtils.throwableInfo(e));
                                    }
                                    //상품 이미지 정보삭제
                                    this.productImageMapper.delete(productImage);
                                });
                        // 상품 이미지 그룹삭제
                        this.productImageGroupMapper.delete(productImageGroup);
                    });

            // 상품 대표이미지가 등록된 경우 -> 파일삭제
            if(StringUtils.hasText(product.getRepImagePath())) {
                try {
                    FileUtils.delete(Paths.get(product.getRepImagePath()));
                } catch (IOException e) {
                    log.error("{}", ErrorUtils.throwableInfo(e));
                }
            }

            // 상품 최종삭제
            this.productMapper.delete(product);
        }
    }

    public Path getImage(Long productId) {
        return Optional.ofNullable(this.productMapper.findById(Product.builder().productId(productId).build()))
                .map(product -> StringUtils.hasText(product.getRepImagePath()) ? Paths.get(product.getRepImagePath()) : null)
                .orElse(null);
    }

    public Path getImage(Long productId, Long productImageGroupId, Long productImageId) {
        return Optional.ofNullable(this.productImageMapper.findById(ProductImage.builder().productId(productId).productImageId(productImageId).productImageGroupId(productImageGroupId).build()))
                .map(productImage -> StringUtils.hasText(productImage.getImagePath()) ? Paths.get(productImage.getImagePath()) : null)
                .orElse(null);
    }

    private void saveViewComponent(Long productId, Constants.ViewComponentType viewComponentType, Map<String, Object> values, int ordinalPosition) {
        // viewComponent 저장 function
        Function<ViewComponent, Long> saveViewComponent1 = viewComponent -> {
            this.viewComponentMapper.save(viewComponent);
            return viewComponent.getViewComponentId();
        };

        switch (viewComponentType) {
            case ACCOMMODATION:
                ViewComponentAccommodationDto viewComponentAccommodationDto = this.objectMapper.convertValue(values, ViewComponentAccommodationDto.class);
                Optional.ofNullable(saveViewComponent1.apply(ViewComponent.builder()
                        .viewComponentId(viewComponentAccommodationDto.getViewComponentId())
                        .viewComponentType(viewComponentType.name())
                        .productId(productId)
                        .description(viewComponentAccommodationDto.getDescription())
                        .title(viewComponentAccommodationDto.getTitle())
                        .ordinal(Optional.ofNullable(viewComponentAccommodationDto.getOrdinal()).orElse(ordinalPosition))
                        .useYn(Optional.ofNullable(viewComponentAccommodationDto.getUseYn()).orElse(Constants.Y))
                        .build())).ifPresent(_viewComponentId -> {
                    ViewComponentAccommodation viewComponentAccommodation = ViewComponentAccommodation.builder()
                            .viewComponentId(_viewComponentId)
                            .address(viewComponentAccommodationDto.getAddress())
                            .contact(viewComponentAccommodationDto.getContact())
                            .fax(viewComponentAccommodationDto.getFax())
                            .url(viewComponentAccommodationDto.getUrl())
                            .build();
                    this.viewComponentAccommodationMapper.save(viewComponentAccommodation);
                });
                break;
            case EDITOR:
                ViewComponentEditorDto viewComponentEditorDto = this.objectMapper.convertValue(values, ViewComponentEditorDto.class);
                // TODO : editor 내 첨부된 이미지 파일 이관

                Optional.ofNullable(saveViewComponent1.apply(ViewComponent.builder()
                                .productId(productId)
                                .title(viewComponentEditorDto.getTitle())
                                .description(viewComponentEditorDto.getDescription())
                                .ordinal(ordinalPosition)
                                .viewComponentType(viewComponentType.name())
                                .build()))
                        .ifPresent(_viewComponentId -> {
                            ViewComponentEditor viewComponentEditor = ViewComponentEditor.builder()
                                    .viewComponentId(_viewComponentId)
                                    .content(viewComponentEditorDto.getContent())
                                    .build();
                            this.viewComponentEditorMapper.save(viewComponentEditor);
                        });
                break ;
        };
    }
}
