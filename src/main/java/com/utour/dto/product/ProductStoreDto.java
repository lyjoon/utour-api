package com.utour.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStoreDto {

    private ProductDto productDto;
    private List<ProductImageGroupDto> productImageGroupList;
    private Map<String, Map<String, Object>> viewComponents;
}
