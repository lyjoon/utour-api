package com.utour.entity;

import com.utour.entity.common.VwComponent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentImageSet extends VwComponent {

    private Long viewComponentImageId;
    private Integer viewComponentImageSeq;
    private String imageSrc;
}
