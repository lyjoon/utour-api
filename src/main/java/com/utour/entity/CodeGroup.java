package com.utour.entity;

import com.utour.common.CommonEntity;
import com.utour.common.Constants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeGroup extends CommonEntity {

    private String groupCode;
    private String groupName;
    private Character useYn;
    private String description;
}
