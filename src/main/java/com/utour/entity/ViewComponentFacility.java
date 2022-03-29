package com.utour.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentFacility {

    private Long facilityComponentId;
    private Long viewComponentId;
    private String iconType;
    private String title;
    private String description;
}
