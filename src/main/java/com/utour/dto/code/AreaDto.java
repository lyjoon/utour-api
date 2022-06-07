package com.utour.dto.code;

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
public class AreaDto extends CommonDto {
    private String areaCode;
    private String areaName;
    private String arrivalCode;
    private String nationCode;
    private Character menuYn;
    private Integer ordinalPosition;
    private Character useYn;
}
