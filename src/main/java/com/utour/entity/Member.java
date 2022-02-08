package com.utour.entity;


import com.utour.entity.common.CommonEntity;
import com.utour.common.Constants;
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
    private Constants.YN useYn;
    private String password;
    private String name;
}
