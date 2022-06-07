package com.utour.dto.product;

import com.utour.dto.code.AreaDto;
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
public class ProductAreaResultsDto extends AreaDto {

    private List<ProductDto> results;
}
