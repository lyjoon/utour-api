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
public class Arrival extends CommonEntity {

    private String arrivalCode;
    private String arrivalName;
    private Character menuYn;
    private Integer ordinalPosition;
    private Character useYn;
}
