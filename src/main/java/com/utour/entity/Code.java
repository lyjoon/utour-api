package com.utour.entity;


import com.utour.entity.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code extends CommonEntity {

    private String groupCode;
    private String code;
    private String codeName;
    private String description;
}
