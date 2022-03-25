package com.utour.dto.code;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {

    private String groupCode;
    private String code;
    private String codeName;
    private String description;
}
