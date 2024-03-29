package com.utour.dto.view;

import com.utour.dto.common.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ViewComponentDto extends CommonDto {

    private Long viewComponentId;
    private Long productId;
    private String viewComponentType;
    private String title;
    private String description;
    private Integer ordinal;
    private Character useYn;
}
