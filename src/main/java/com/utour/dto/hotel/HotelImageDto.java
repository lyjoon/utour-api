package com.utour.dto.hotel;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelImageDto {

    private Long hotelId;
    private Integer imageId;
    private String src;
    private String alt;
    private String description;
}
