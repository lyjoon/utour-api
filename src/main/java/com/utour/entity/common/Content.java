package com.utour.entity.common;

import com.utour.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public abstract class Content extends CommonEntity {

    private String title;
    private String content;
    private String writer;
    private int pv;

    abstract public Long getId();
}
