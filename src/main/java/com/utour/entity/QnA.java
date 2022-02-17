package com.utour.entity;

import com.utour.annotation.EncryptValue;
import com.utour.entity.common.Content;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnA extends Content {

    private Long qnaId;
    private Character privateYn;
    @EncryptValue(length = 20) private String password;

    @Override
    public Long getId() {
        return this.qnaId;
    }
}
