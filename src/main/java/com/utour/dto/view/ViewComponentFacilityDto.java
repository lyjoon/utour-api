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
@NoArgsConstructor
@AllArgsConstructor
public class ViewComponentFacilityDto extends CommonDto {

    private Long viewComponentId;
    private Long viewComponentSeq;
    private String iconType;
    private String title;
    private String description;
}
