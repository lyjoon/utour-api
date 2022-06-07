package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @deprecated 미사용개체
 */
@Getter
@Deprecated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NationArea extends CommonEntity {

    private String nationCode;
    private String areaCode;
    private String areaName;
    private Character useYn;
}
