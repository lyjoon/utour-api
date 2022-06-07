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
public class Area extends CommonEntity {

    private String areaCode;
    private String areaName;
    private String arrivalCode;
    private String nationCode;
    private Character menuYn;
    private Integer ordinalPosition;
    private Character useYn;
}
