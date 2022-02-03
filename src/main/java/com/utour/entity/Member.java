package com.utour.entity;


import com.utour.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CommonEntity {

    private String userId;
    private String useYn;
    private String password;
    private String name;
}
