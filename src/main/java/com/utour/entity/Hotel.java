package com.utour.entity;

import com.utour.entity.common.Content;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel extends Content {

    private Long hotelId;
    private String nationCode;
    private String areaCode;
    private Character useYn;

    private String hotelName;
    private String hotelClass;
    private String hotelUrl;
    private String hotelContact;
    private String hotelAddress;

    @Override
    public Long getId() {
        return this.getHotelId();
    }
}
