package com.utour.dto.home;

import com.utour.dto.common.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class HomeCarouselDto extends CommonDto {
    private Long homeCarouselId;
    private String title;
    private String imageSrc;
    private String linkUrl;
    private Integer ordinalPosition;
    private Character useYn;
}
