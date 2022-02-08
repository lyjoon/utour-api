package com.utour.entity;

import com.utour.entity.common.CommonEntity;
import com.utour.common.Constants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Area extends CommonEntity {

    private String nationCode;
    private String areaCode;
    private String areaName;
    private Constants.YN useYn;
}
