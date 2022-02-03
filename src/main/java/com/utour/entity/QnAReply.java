package com.utour.entity;

import com.utour.entity.common.BaseAt;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnAReply extends BaseAt {

    private Long qnaId;
    private Integer qnaReplyId;
    private String writer;
    private String content;
    private String password;
    private Character privateYn;
    private Character adminYn;
}
