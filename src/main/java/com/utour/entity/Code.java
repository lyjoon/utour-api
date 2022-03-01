package com.utour.entity;


import com.utour.common.CommonEntity;
import com.utour.common.Constants;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Code extends CommonEntity {

    private Constants.GroupCode groupCode;
    private String code;
    private String codeName;
    private String description;
}
