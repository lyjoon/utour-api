package com.utour.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentImage {

    private Long imageComponentId;
    private Long viewComponentId;
    private String displayType;
    private String content;
    private String title;
    private String description;
}
