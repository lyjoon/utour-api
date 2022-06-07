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
public class ArrivalDto extends CommonDto {
    private String arrivalCode;
    private String arrivalName;
    private Character menuYn;
    private Integer ordinalPosition;
    private Character useYn;
}
