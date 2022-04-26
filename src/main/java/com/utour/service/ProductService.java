package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.product.*;
import com.utour.dto.view.ViewComponentAccommodationDto;
import com.utour.dto.view.ViewComponentDto;
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
        Constants.ProductType productType = Constants.ProductType.valueOf(productDto.getProductType());
        Product product = Product.builder()
                .productId(productDto.getProductId())
                .productType(productDto.getProductType())
                .writer(productDto.getWriter())
                .repImageSrc(productDto.getRepImageSrc())
                .nationCode(productDto.getNationCode())
                .areaCode(productDto.getAreaCode())
                .title(productDto.getTitle())
                .content(productDto.getContent())
                .useYn(productDto.getUseYn())
                .build();
        this.productMapper.save(product);
        switch (productType) {
            case HOTEL:
                // ACCOMMODATION 기본추가
                this.viewComponentService.save(ViewComponentAccommodationDto.builder()
                        .productId(product.getProductId())
                        .viewComponentType(Constants.ViewComponentType.ACCOMMODATION.name())
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
                productImageDto.setProductId(product.getProductId());
                this.save(productImageDto);
            });
        }

        if(!Optional.ofNullable(productViewDto.getProductImageGroups()).map(List::isEmpty).orElse(true)) {
            productViewDto.getProductImageGroups().forEach(productImageGroupDto -> {
                productImageGroupDto.setProductId(product.getProductId());
                this.save(productImageGroupDto);
            });
        }

        if(!Optional.ofNullable(productViewDto.getViewComponents()).map(List::isEmpty).orElse(true)) {
            productViewDto.getViewComponents().forEach(viewComponentDto -> {
                viewComponentDto.setProductId(product.getProductId());
                this.viewComponentService.save(viewComponentDto);
            });
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

        boolean exists = Optional.ofNullable(this.productMapper.exists(product)).orElse(false);
        if(!exists) throw new InternalException(getMessage("error.service.product.not-exists"));
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
