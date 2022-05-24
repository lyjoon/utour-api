package com.utour.dto.product;

import com.utour.dto.view.ViewComponentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductViewDto extends ProductDto {

    private List<? extends ViewComponentDto> viewComponents;
    private List<ProductImageGroupDto> productImageGroups;

}
