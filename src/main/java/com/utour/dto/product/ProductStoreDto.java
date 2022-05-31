package com.utour.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(value = "product")
    private ProductDto productDto;
    private List<ProductImageGroupDto> productImageGroupList;
    private List<Map<String, Object>> viewComponents;
}
