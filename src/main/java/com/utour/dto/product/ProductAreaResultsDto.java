package com.utour.dto.product;

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
public class ProductAreaResultsDto {

    private String arrivalCode;
    private String areaCode;
    private String areaName;
    private List<ProductDto> results;
}
