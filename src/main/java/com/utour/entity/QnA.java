package com.utour.entity;

import com.utour.entity.common.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnA extends Board {

    private Long qnaId;
    private Character privateYn;
    private String password;

    @Override
    public Long getId() {
        return this.qnaId;
    }
}
