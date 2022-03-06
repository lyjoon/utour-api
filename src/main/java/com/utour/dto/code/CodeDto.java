package com.utour.dto.code;

import com.utour.common.Constants;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {

    private Constants.GroupCode groupCode;
    private String code;
    private String codeName;
    private String description;
}
