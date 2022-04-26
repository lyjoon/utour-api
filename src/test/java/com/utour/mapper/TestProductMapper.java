package com.utour.mapper;

import com.utour.TestLocalApplication;
import com.utour.common.Constants;
import com.utour.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestProductMapper extends TestLocalApplication {


    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testSave() {
        Product product = Product.builder()
                .title("TEST")
                .areaCode("ICH")
                .nationCode("KR")
                .content("ㅁㄴㅇㅁㄴㅇ")
                .productType(Constants.ProductType.HOTEL.name())
                .useYn(Constants.Y)
                .build();
        this.productMapper.save(product);
        log.info("{}", product.toString());
    }

}
