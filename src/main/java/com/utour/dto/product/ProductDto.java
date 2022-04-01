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
    private String nationCode;
    private String areaCode;
    private String repImageSrc;
    private Character useYn;
    private String title;
    private String content;
    private String writer;
}