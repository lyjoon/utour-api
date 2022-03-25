package com.utour.dto.code;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeGroupDto {

    private String groupCode;
    private String groupName;
    private Character useYn;
    private String description;

    private List<CodeDto> codeList;
}
