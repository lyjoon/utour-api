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
public class QnAReply extends CommonEntity {

    private Long qnaId;
    private Integer qnaReplyId;
    private String writer;
    private String content;
    private String password;
    private Constants.YN privateYn;
    private Constants.YN adminYn;
}
