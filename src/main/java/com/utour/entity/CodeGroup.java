package com.utour.entity;

import com.utour.entity.common.BaseAt;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeGroup extends BaseAt {

    private String groupCode;
    private String groupName;
    private String codeName;
    private String description;
}
