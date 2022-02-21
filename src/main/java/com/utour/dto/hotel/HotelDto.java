package com.utour.dto.hotel;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {

    private Long hotelId;
    private String nationCode;
    private String areaCode;
    private Character useYn;

    private String hotelName;
    private String hotelClass;
    private String hotelUrl;
    private String hotelContact;
    private String hotelAddress;

}
