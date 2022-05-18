package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Carousel extends CommonEntity {

    private Long carouselId;
    private String title;
    private String imageSrc;
    private String linkUrl;
    private Integer ordinalPosition;
    private Character useYn;
}
