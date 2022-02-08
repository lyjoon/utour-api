package com.utour.entity;

import com.utour.entity.common.Attachment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerAttach extends Attachment {

    private Long answerId;
    private Integer answerAttachId;

}
