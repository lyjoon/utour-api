package com.utour.entity.common;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class Content extends CommonEntity {

    private String title;
    private String content;
    private String writer;

    abstract public Long getId();
}
