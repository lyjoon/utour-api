package com.utour.entity;

import com.utour.annotation.Encrypt;
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
    private Long qnaReplyId;
    private String writer;
    private String content;
    @Encrypt
    private String password;
    private Character privateYn;
    private Character adminYn;
}
