package com.utour.dto.product;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;
    private String productType;
    private String displayType;
    private String title;
    private String description;
    private int ordinal;
    private String src;
    private Character useYn;
}
