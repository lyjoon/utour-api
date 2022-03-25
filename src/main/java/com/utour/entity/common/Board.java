package com.utour.entity.common;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class Board extends CommonEntity {

    private String title;
    private String content;
    private String writer;
    private Integer pv;

    abstract public Long getId();
}
