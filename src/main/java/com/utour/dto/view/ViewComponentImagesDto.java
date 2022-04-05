package com.utour.dto.view;

import com.utour.dto.common.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ViewComponentImagesDto extends CommonDto {

    private Long viewComponentId;
    private Long viewComponentSeq;
    private String imageSrc;
    private String title;
    private String description;
}
