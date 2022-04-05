package com.utour.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ViewComponentAccommodationDto extends ViewComponentDto{

    private String url;
    private String address;
    private String contact;
    private String fax;
}
