package com.utour.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentImageSet {

    private Long imageComponentId;
    private Integer imageComponentSeq;
    private String imageSrc;
    private String title;
    private String description;
}
