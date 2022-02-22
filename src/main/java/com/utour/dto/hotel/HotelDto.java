package com.utour.dto.hotel;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {

    private Long hotelId;
    private String nationCode;
    private String nationName;
    private String areaCode;
    private String areaName;
    private Character useYn;

    private String hotelName;
    private String hotelClass;
    private String hotelUrl;
    private String hotelContact;
    private String hotelAddress;

}
