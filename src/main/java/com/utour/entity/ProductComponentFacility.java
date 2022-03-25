package com.utour.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductComponentFacility {

    private Long productComponentFacilityId;
    private Long productComponentId;
    private String iconType;
    private String description;
}
