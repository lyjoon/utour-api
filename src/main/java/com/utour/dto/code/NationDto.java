package com.utour.dto.code;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NationDto {

    private String continentCode;
    private String nationCode;
    private String nationName;
    private Character useYn;

    private List<NationAreaDto> nationAreaList;
}
