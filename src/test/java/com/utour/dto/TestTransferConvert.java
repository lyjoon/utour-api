package com.utour.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utour.TestLocalApplication;
import com.utour.entity.Product;
import com.utour.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TestTransferConvert extends TestLocalApplication {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testConvert() {
        Product product =Product.builder()
                .productType("sr")
                .productId(1L)
                .description("테스트")
                .title("제목")
                .build();

        // ProductDto productDto = this.objectMapper.convertValue(product, ProductDto.class);
        ProductDto productDto = new ModelMapper().map(product, ProductDto.class);
        log.info("{}", productDto.toString());
    }
}
