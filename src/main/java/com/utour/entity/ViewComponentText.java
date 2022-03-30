package com.utour.entity;

import com.utour.entity.common.VwComponent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentText extends VwComponent {

    private Long viewComponentTextId;
    private Long viewComponentId;
    private String content;

}
