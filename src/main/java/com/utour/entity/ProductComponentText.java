package com.utour.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductComponentText {

    private Long productComponentTextId;
    private Long productComponentId;
    private String title;
    private String content;
    private String description;
}
