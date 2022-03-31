package com.utour.entity;

import com.utour.entity.common.Content;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends Content {

    private Long productId;
    private String productType;
    private String nationCode;
    private String areaCode;
    private String repImageSrc;
    private Character useYn;

    @Override
    public Long getId() {
        return this.productId;
    }
}
