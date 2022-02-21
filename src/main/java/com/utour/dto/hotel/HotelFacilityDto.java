package com.utour.dto.hotel;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelFacilityDto {

    private Long hotelId;
    private Integer facilityId;
    private String icon;
    private String description;
    private Character useYn;
}
