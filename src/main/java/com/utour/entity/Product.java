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
    private String displayType;
    private String title;
    private String description;
    private int ordinal;
    private String src;
    private Character useYn;
}
