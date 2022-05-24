package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.controller.ProductController;
import com.utour.dto.PagingResultDto;
import com.utour.dto.display.CommerceDto;
import com.utour.dto.product.*;
import com.utour.dto.view.ViewComponentAccommodationDto;
import com.utour.dto.view.ViewComponentDto;
import com.utour.entity.*;
import com.utour.exception.InternalException;
import com.utour.mapper.*;
import com.utour.util.ErrorUtils;
import com.utour.util.FileUtils;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends CommonService {

    private final ProductMapper productMapper;
    private final ProductImageGroupMapper productImageGroupMapper;
    private final ProductImageMapper productImageMapper;
    private final CommerceMapper commerceMapper;
    private final ViewComponentService viewComponentService;

    @Value(value = "${app.file-upload-storage.temp:}")
    private Path tempPath;

    @Value(value = "${app.file-upload-storage.content:}")
    private Path contentPath;

    @Value(value = "${app.file-upload-storage.product:}")
    private Path productPath;


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

        // 대표 이미지가 있는 경우, content-path 에 저장시킨 뒤 product.rep_image_src 값을 외부에서 접근가능한 url 로 지정해준다.
        Optional.ofNullable(repProductImageFile).ifPresent(multipartFile -> {
            try {
                Path repImageStorePath = FileUtils.uploadFile(productPath, currentDate, repProductImageFile);
                productDto.setRepImageSrc(this.contextPath + ProductController.PRODUCT_IMAGE_LINK_URL + repImageStorePath.toFile().getName());
            } catch (IOException e) {
                log.warn("{}", ErrorUtils.throwableInfo(e));
                productDto.setRepImageSrc(null);
            }
        });

        // 상품 기본개요를 저장한다.(productId 값을 반환받아야 함)
        Product product = Product.builder()
                .areaCode(productDto.getAreaCode())
                .nationCode(productDto.getNationCode())
                .content(productDto.getContent())
                .productType(productDto.getProductType())
                .repImageSrc(productDto.getRepImageSrc())
                .title(productDto.getTitle())
                .useYn(Optional.ofNullable(productDto.getUseYn()).orElse(Constants.Y))
                .build();

        // 신규저장
        this.productMapper.save(product);

        // 이미지 그룹을 저장한다.(신규저장이니 기존데이터 고려할 것이 없음)
        // 이미지 그룹 내 등록된 이미지 파일명과 전달받은 상품 이미지 목록(productImageFiles)이 일치하는 경우 저장함.
        Optional.ofNullable(productStoreDto.getProductImageGroupList()).ifPresent(productImageGroupList -> {
            // 이미지 그룹 저장 순환문
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
                                if(multipartFile.getOriginalFilename().equals(originFileName)) {
                                    // 매칭되는 파일확인 -> productPath 경로에 저장 후 데이터를 테이블에 저장.
                                    try {
                                        Path path = FileUtils.uploadFile(productPath, currentDate, multipartFile);
                                        String imageSrc = this.contextPath + ProductController.PRODUCT_IMAGE_LINK_URL + path.toFile().getName();
                                        ProductImage productImage = ProductImage.builder()
                                                .productId(product.getProductId())
                                                .imageSrc(imageSrc)
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
            }
        });

        // 화면구성요소 목록을 저장함.
        // editor 유형인 경우 tempPath 경로로 첨부된 이미지 파일이 있는 경우 => 이미지 파일 저장경로로 이관 뒤 경로를 수정(replace)해야함
        if(!Optional.ofNullable(productStoreDto.getViewComponents()).map(Map::isEmpty).orElse(true)) {
            Iterator<Map.Entry<String, Map<String, Object>>> iterator = productStoreDto.getViewComponents().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map<String, Object>> entry = iterator.next();

                // 상호 정의된 Component 유형이 아닌경우 버림.
                Constants.ViewComponentType viewComponentType = Arrays.stream(Constants.ViewComponentType.values())
                        .filter(type -> type.name().equals(entry.getKey())).findFirst().orElse(null);

                if (Objects.isNull(viewComponentType)) continue;

                switch (viewComponentType) {
                    case ACCOMMODATION :
                        ViewComponentAccommodation viewComponentAccommodation = this.objectMapper.convertValue(entry.getValue(), ViewComponentAccommodation.class);
                        //this.viewComponentService.sa(viewComponentAccommodation);
                        log.info("{}", viewComponentAccommodation.toString());
                        break;
                    case MARKDOWN:
                        ViewComponentEditor viewComponentEditor = this.objectMapper.convertValue(entry.getValue(), ViewComponentEditor.class);
                        log.info("{}", viewComponentEditor.toString());
                        break;
                }
            }
        }
    }

    protected void save(ProductImageDto productImageDto) {
        this.productImageMapper.save(ProductImage.builder()
                .productImageId(productImageDto.getProductId())
                .imageSrc(productImageDto.getImageSrc())
                .productImageGroupId(null)
                .imageSrc(productImageDto.getImageSrc())
                .description(productImageDto.getDescription())
                .build());
    }

    protected void save(ProductImageGroupDto productImageGroupDto) {
        ProductImageGroup productImageGroup = ProductImageGroup.builder()
                .productId(productImageGroupDto.getProductId())
                .groupName(productImageGroupDto.getGroupName())
                .productImageGroupId(productImageGroupDto.getProductImageGroupId())
                .build();
        this.productImageGroupMapper.save(productImageGroup);
        if(!Optional.ofNullable(productImageGroupDto.getProductImages()).map(List::isEmpty).orElse(true)) {
            productImageGroupDto.getProductImages().forEach(productImageDto -> {
                productImageDto.setProductImageId(productImageGroupDto.getProductId());
                productImageDto.setProductImageGroupId(productImageGroupDto.getProductImageGroupId());
                this.save(productImageDto);
            });
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    protected void delete(CommerceDto commerceDto) {
        Commerce commerce = Commerce.builder().commerceId(commerceDto.getCommerceId()).build();
        Optional.ofNullable(this.commerceMapper.findAll(commerce))
                .ifPresent(list -> list.forEach(present -> this.commerceMapper.delete(present)));
    }

    /**
     * 상품 삭제
     * @param productDto
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(ProductDto productDto) {
        // 파라미터 설정
        Product product = Product.builder()
                .productId(productDto.getProductId())
                .build();
        // 상품요소 확인
        boolean exists = Optional.ofNullable(this.productMapper.exists(product)).orElse(false);
        if(!exists) {
            throw new InternalException(getMessage("error.service.product.not-exists"));
        }

        // 하위 데이터 HOME_PRESENT 삭제
        this.delete(CommerceDto.builder().productId(product.getProductId()).build());

        // 상품 이미지 삭제
        this.delete(ProductImageDto.builder().productId(product.getProductId()).build());
        // 상품 이미지그룹 삭제
        this.delete(ProductImageGroupDto.builder().productId(product.getProductId()).build());
        // 화면요소 삭제
        this.viewComponentService.deleteAll(ViewComponentDto.builder().productId(productDto.getProductId()).build());
        // 상품삭제
        this.productMapper.delete(product);
    }

    /**
     * 상품 이미지 삭제
     * @param productImageDto
     */
    private void delete(ProductImageDto productImageDto) {
        ProductImage productImage = ProductImage.builder()
                .productId(productImageDto.getProductId())
                .build();
        if(this.productImageMapper.exists(productImage)) {
            this.productImageMapper.delete(productImage);
        }
    }

    /**
     * 상품 이미지 그룹삭제
     * @param productImageGroupDto
     */
    private void delete(ProductImageGroupDto productImageGroupDto) {
        ProductImageGroup productImageGroup = ProductImageGroup.builder()
                .productId(productImageGroupDto.getProductId())
                .build();
        if(this.productImageGroupMapper.exists(productImageGroup)) {
            this.productImageGroupMapper.delete(productImageGroup);
        }
    }

    public PagingResultDto getList(ProductQueryDto productPagingDto) {
        List<ProductDto> result = this.productMapper.findPage(productPagingDto)
                .stream()
                .map(vo -> this.convert(vo, ProductDto.class))
                .collect(Collectors.toList());
        return PagingResultDto.builder()
                .page(productPagingDto.getPage())
                .limit(productPagingDto.getLimit())
                .result(result)
                .build();
    }

    public ProductViewDto get(Long productId) {
        return Optional.ofNullable(this.productMapper.findById(Product.builder().productId(productId).build()))
                .map(v -> {
                    ProductViewDto productViewDto = this.convert(v, ProductViewDto.class);
                    productViewDto.setViewComponents(this.viewComponentService.getList(ViewComponentDto.builder()
                            .productId(v.getProductId())
                            .build()));
                    return productViewDto;
                }).orElse(null);
    }
}
