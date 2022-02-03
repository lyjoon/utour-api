package com.utour.entity;

import com.utour.entity.common.BaseAt;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Area extends BaseAt {

    private String nationCode;
    private String areaCode;
    private String areaName;
    private Character useYn;
}
