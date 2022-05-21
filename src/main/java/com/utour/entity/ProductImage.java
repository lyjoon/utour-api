package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage extends CommonEntity {

    private Long productImageId;
    private Long productId;
    private Long productImageGroupId;
    private String imageSrc;
    private String title;
    private String description;
}
