package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.controller.ProductController;
import com.utour.dto.PagingResultDto;
import com.utour.dto.product.*;
import com.utour.dto.view.ViewComponentAccommodationDto;
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
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
     * 여행상품 신규저장(insert)
     * @param productStoreDto
     * @param repProductImageFile
     * @param productImageFiles
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(ProductStoreDto productStoreDto, MultipartFile repProductImageFile, MultipartFile[] productImageFiles) throws InternalException {

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
                productBuilder.repImageSrc(this.contextPath + ProductController.PRODUCT_IMAGE_LINK_URL + file.getName());
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
                                        String imageSrc = this.contextPath + ProductController.PRODUCT_IMAGE_LINK_URL + path.toFile().getName();
                                        ProductImage productImage = ProductImage.builder()
                                                .productId(product.getProductId())
                                                .imageSrc(imageSrc)
                                                .imagePath(path.toFile().getPath())
                                                .productImageGroupId(productImageGroup.getProductImageGroupId())
                                                .description(productImageDto.getOriginFileName())
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

                switch (viewComponentType) {
                    case ACCOMMODATION :
                        ViewComponentAccommodationDto viewComponentAccommodationDto = this.objectMapper.convertValue(entry.getValue(), ViewComponentAccommodationDto.class);

                        Optional.ofNullable(function.apply(() -> ViewComponent.builder()
                                        .productId(product.getProductId())
                                        .title(viewComponentAccommodationDto.getTitle())
                                        .description(viewComponentAccommodationDto.getDescription())
                                        .ordinal(atomicInteger.getAndIncrement())
                                        .viewComponentType(viewComponentType.name())
                                        .build()))
                                .ifPresent(viewComponentId -> {
                                    ViewComponentAccommodation viewComponentAccommodation = ViewComponentAccommodation.builder()
                                            .viewComponentId(viewComponentId)
                                            .address(viewComponentAccommodationDto.getAddress())
                                            .contact(viewComponentAccommodationDto.getContact())
                                            .fax(viewComponentAccommodationDto.getFax())
                                            .url(viewComponentAccommodationDto.getUrl())
                                            .build();
                                    this.viewComponentAccommodationMapper.save(viewComponentAccommodation);
                                });

                        break;
                    case EDITOR:
                        ViewComponentEditorDto viewComponentEditorDto = this.objectMapper.convertValue(entry.getValue(), ViewComponentEditorDto.class);

                        // TODO : 내용안에 첨부된 temp 경로 이미지파일 -> content 로 이관처리

                        Optional.ofNullable(function.apply(() -> ViewComponent.builder()
                                .productId(product.getProductId())
                                .title(viewComponentEditorDto.getTitle())
                                .description(viewComponentEditorDto.getDescription())
                                .ordinal(atomicInteger.getAndIncrement())
                                .viewComponentType(viewComponentType.name())
                                .build()))
                                .ifPresent(viewComponentId -> {
                                    ViewComponentEditor viewComponentEditor = ViewComponentEditor.builder()
                                            .viewComponentId(viewComponentId)
                                            .content(viewComponentEditorDto.getContent())
                                            .build();
                                    this.viewComponentEditorMapper.save(viewComponentEditor);
                                });

                        break;
                }
            }
        }
    }


    /**
     * 상품 삭제
     * @param productId 상품 ID
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long productId) {

        // 등록된 상품이 유효한지 조회함
        Product product = Product.builder().productId(productId).build();
        boolean existsProduct = this.productMapper.exists(product);
        valid : {
            if(!existsProduct) break valid;

            // 등록된 커머스 조회함 -> 삭제
            Commerce commerce = Commerce.builder().productId(productId).build();
            if(commerceMapper.exists(commerce)) {
                this.commerceMapper.delete(commerce);
            }

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

        // 상품 최종삭제
        this.productMapper.delete(product);
    }

}
