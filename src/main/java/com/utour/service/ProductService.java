package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductQueryDto;
import com.utour.entity.Product;
import com.utour.entity.ViewComponent;
import com.utour.entity.ViewComponentImages;
import com.utour.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends CommonService {

    private final ProductMapper productMapper;

    /**
     * 상품정보 생성
     * @param productDto
     */
    public void create(ProductDto productDto) {
        Constants.PRODUCT_TYPE productType = Constants.PRODUCT_TYPE.valueOf(productDto.getProductType());
        this.productMapper.save(this.convert(productDto, Product.class));
        switch (productType) {
            case HOTEL:

                break;
        }
    }

    public void delete(ProductDto productDto) {
        this.productMapper.delete(this.convert(productDto, Product.class));
    }

    public List<ProductDto> getList(ProductQueryDto productPagingDto) {
        return this.productMapper.findPage(productPagingDto)
                .stream()
                .map(vo -> this.convert(vo, ProductDto.class))
                .collect(Collectors.toList());
    }
}
