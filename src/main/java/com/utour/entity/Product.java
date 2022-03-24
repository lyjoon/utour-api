package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends CommonEntity {

    private Long productId;
    private String productType;
    private String nationCode;
    private String areaCode;
    private String title;
    private String writer;
    private String description;
    private String repImageSrc;
    private Character useYn;
}
