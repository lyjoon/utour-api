package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends CommonEntity {

    private Long answerId;
    private Long inquiryId;
    private String title;
    private String writer;
    private String content;
    private Character emailReplyYn;
    private LocalDateTime emailReplyAt;
}
