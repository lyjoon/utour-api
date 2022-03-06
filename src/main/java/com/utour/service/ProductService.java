package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.product.ProductDto;
import com.utour.dto.product.ProductPagingDto;
import com.utour.entity.Product;
import com.utour.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends CommonService {

    private final ProductMapper productMapper;

    public void save(ProductDto productDto) {
        this.productMapper.save(this.convert(productDto, Product.class));
    }

    public void delete(ProductDto productDto) {
        this.productMapper.delete(this.convert(productDto, Product.class));
    }

    public List<ProductDto> getPageList(ProductPagingDto productPagingDto) {
        return this.productMapper.findPage(productPagingDto)
                .stream()
                .map(vo -> this.convert(vo, ProductDto.class))
                .collect(Collectors.toList());
    }
}