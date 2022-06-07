package com.utour.dto.code;

import lombok.*;

/**
 * @deprecated 미사용개체
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class NationAreaDto {

    private String nationCode;
    private String areaCode;
    private String areaName;
    private Character useYn;
}
