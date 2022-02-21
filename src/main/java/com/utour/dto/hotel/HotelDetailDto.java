package com.utour.dto.hotel;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDetailDto {

    private HotelDto hotelDto;
    private List<HotelFacilityDto> hotelFacilities;
    private List<HotelImageDto> hotelImages;
}
