package com.utour.dto.display;

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
public class CarouselDto extends CommonDto {
    private Long carouselId;
    private String title;
    private String imagePath;
    private String linkUrl;
    private Integer ordinalPosition;
    private Character useYn;
}
