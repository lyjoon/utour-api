package com.utour.entity;

import com.utour.entity.common.VwComponent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentFacility extends VwComponent {

    private Long viewComponentFacilityId;
    private Long viewComponentId;
    private String iconType;
}
