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
public class hotelFacility extends CommonEntity {

    private Long hotelId;
    private Integer facilityId;
    private String icon;
    private String description;
    private Character useYn;
}
