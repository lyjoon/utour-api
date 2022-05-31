package com.utour.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utour.common.Constants;
import com.utour.dto.view.ViewComponentDto;
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
public class ProductViewDto extends ProductDto {

    @JsonProperty(value = "product")
    private ProductDto productDto;
    private List<ProductImageGroupDto> productImageGroups;
    //private Map<Constants.ViewComponentType, ? extends ViewComponentDto> viewComponents;
    private List<? extends ViewComponentDto> viewComponents;
}
