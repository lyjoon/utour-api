package com.utour.entity;

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
public class Answer extends CommonEntity {

    private Long inquiryId;
    private Integer inquiryAnswerId;
    private String title;
    private String writer;
    private String content;
    private String emailReplyYn;
    private String emailReplyAt;
}
