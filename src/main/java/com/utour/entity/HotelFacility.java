package com.utour.entity;

import com.utour.common.CommonEntity;
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
public class HotelFacility extends CommonEntity {

    private Long hotelId;
    private Integer facilityId;
    private String icon;
    private String description;
    private Constants.YN useYn;
}
