package com.utour.dto.product;

import com.utour.dto.PaginationQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductQueryDto extends PaginationQueryDto {

    private String productType;
    private Long productId;
    private String nationCode;
    private String areaCode;
}
