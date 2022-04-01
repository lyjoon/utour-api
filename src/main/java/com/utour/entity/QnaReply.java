package com.utour.entity;

import com.utour.annotation.EncryptValue;
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
public class QnaReply extends CommonEntity {

    private Long qnaId;
    private Integer qnaReplyId;
    private String writer;
    private String content;
    @EncryptValue(length = 20)
    private String password;
    private Character privateYn;
    private Character adminYn;
}
