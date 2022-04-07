package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductImageDto;
import com.utour.dto.product.ProductQueryDto;
import com.utour.dto.product.ProductViewDto;
import com.utour.dto.view.ViewComponentAccommodationDto;
import com.utour.dto.view.ViewComponentDto;
import com.utour.dto.view.ViewComponentFacilityDto;
import com.utour.entity.Product;
import com.utour.entity.ProductImage;
import com.utour.entity.ProductImageGroup;
import com.utour.exception.InternalException;
import com.utour.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends CommonService {

    private final ProductMapper productMapper;
    private final ProductImageGroupMapper productImageGroupMapper;
    private final ProductImageMapper productImageMapper;

    private final ViewComponentService viewComponentService;

    /**
     * 상품정보 생성
     * @param productDto
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(ProductDto productDto) {
        Constants.PRODUCT_TYPE productType = Constants.PRODUCT_TYPE.valueOf(productDto.getProductType());
        Product product = this.convert(productDto, Product.class);
        this.productMapper.save(product);
        switch (productType) {
            case HOTEL:
                // ACCOMMODATION 기본추가
                this.viewComponentService.save(ViewComponentAccommodationDto.builder()
                        .productId(product.getProductId())
                        .build());
                break;
        }
    }

    public void save(ProductDto productDto) {
        this.productMapper.save(Product.builder()
                .productId(productDto.getProductId())
                .productType(productDto.getProductType())
                .useYn(productDto.getUseYn())
                .content(productDto.getContent())
                .title(productDto.getTitle())
                .areaCode(productDto.getAreaCode())
                .nationCode(productDto.getNationCode())
                .repImageSrc(productDto.getRepImageSrc())
                .writer(productDto.getWriter())
                .build());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(ProductViewDto productViewDto) {
        Product product = Product.builder()
                .productId(productViewDto.getProductId())
                .productType(productViewDto.getProductType())
                .useYn(productViewDto.getUseYn())
                .content(productViewDto.getContent())
                .title(productViewDto.getTitle())
                .areaCode(productViewDto.getAreaCode())
                .nationCode(productViewDto.getNationCode())
                .repImageSrc(productViewDto.getRepImageSrc())
                .writer(productViewDto.getWriter())
                .build();

        this.productMapper.save(product);


        if(!Optional.ofNullable(productViewDto.getProductImages()).map(List::isEmpty).orElse(true)) {
            productViewDto.getProductImages().forEach(productImageDto -> {
                this.productImageMapper.save(ProductImage.builder()
                        .productImageId(product.getProductId())
                        .imageSrc(productImageDto.getImageSrc())
                        .productImageGroupId(null)
                        .imageSrc(productImageDto.getImageSrc())
                        .description(productImageDto.getDescription())
                        .build());
            });
        }

        if(!Optional.ofNullable(productViewDto.getProductImageGroups()).map(List::isEmpty).orElse(true)) {
            productViewDto.getProductImageGroups().forEach(productImageGroupDto -> {
                ProductImageGroup productImageGroup = ProductImageGroup.builder()
                        .productId(product.getProductId())
                        .groupName(productImageGroupDto.getGroupName())
                        .productImageGroupId(productImageGroupDto.getProductImageGroupId())
                        .build();
                this.productImageGroupMapper.save(productImageGroup);
                if(!Optional.ofNullable(productImageGroupDto.getProductImages()).map(List::isEmpty).orElse(true)) {
                    productImageGroupDto.getProductImages().forEach(productImageDto -> {
                        this.productImageMapper.save(ProductImage.builder()
                                .productImageId(product.getProductId())
                                .productImageGroupId(productImageGroup.getProductImageGroupId())
                                .imageSrc(productImageDto.getImageSrc())
                                .productImageGroupId(null)
                                .imageSrc(productImageDto.getImageSrc())
                                .description(productImageDto.getDescription())
                                .build());
                    });
                }
            });
        }

        if(!Optional.ofNullable(productViewDto.getViewComponents()).map(List::isEmpty).orElse(true)) {
            productViewDto.getViewComponents().forEach(viewComponentDto ->
                    this.viewComponentService.save(viewComponentDto));
        }
    }

    /**
     * 상품 삭제
     * @param productDto
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(ProductDto productDto) {
        // 상품요소 확인
        Product product = Product.builder()
                .productId(productDto.getProductId())
                .build();

        Optional.ofNullable(this.productMapper.exists(product))
                .ifPresent(aBoolean -> {
                    if(!aBoolean) throw new InternalException(getMessage("error.service.product.delete.not-exists"));
                });

        // 상품이미지 삭제
        ProductImageGroup productImageGroup = ProductImageGroup.builder()
                .productId(product.getProductId())
                .build();

        if(this.productImageGroupMapper.exists(productImageGroup)) {
            this.productImageGroupMapper.delete(productImageGroup);
        }

        ProductImage productImage = ProductImage.builder()
                .productId(product.getProductId())
                .build();
        if(this.productImageMapper.exists(productImage)) {
            this.productImageMapper.delete(productImage);
        }

        // 화면요소 삭제
        this.viewComponentService.getList(ViewComponentDto.builder().productId(productDto.getProductId()).build())
                .forEach(viewComponentService::delete);

        // 상품삭제
        this.productMapper.delete(product);
    }

    public PaginationResultDto getList(ProductQueryDto productPagingDto) {
        List<ProductDto> result = this.productMapper.findPage(productPagingDto)
                .stream()
                .map(vo -> this.convert(vo, ProductDto.class))
                .collect(Collectors.toList());
        return PaginationResultDto.builder()
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
