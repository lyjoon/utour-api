package com.utour.entity;

import com.utour.entity.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HotelImage extends CommonEntity {

    private Long hotelId;
    private Integer imageId;
    private String src;
    private String alt;
    private String description;
}
