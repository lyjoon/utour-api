package com.utour.dto.code;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NationAreaDto {

    private String nationCode;
    private String areaCode;
    private String areaName;
    private Character useYn;
}
