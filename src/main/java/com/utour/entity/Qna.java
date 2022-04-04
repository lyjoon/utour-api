package com.utour.entity;

import com.utour.annotation.Encrypt;
import com.utour.entity.common.Content;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends Content {

    private Long qnaId;
    private Character privateYn;
    @Encrypt
    private String password;
    private int pv;

    @Override
    public Long getId() {
        return this.qnaId;
    }
}
