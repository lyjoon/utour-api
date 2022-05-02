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
public class HomeCarousel extends CommonEntity {

    private Long homeCarouselId;
    private String title;
    private String imageSrc;
    private String linkUrl;
    private Integer ordinalPosition;
    private Character useYn;
}
