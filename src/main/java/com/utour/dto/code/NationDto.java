package com.utour.dto.code;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NationDto {

    private String nationCode;
    private String nationName;
    private Character useYn;
}
