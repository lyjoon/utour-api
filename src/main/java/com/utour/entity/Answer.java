package com.utour.entity;

import com.utour.entity.common.CommonEntity;
import com.utour.common.Constants;
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

    private Long inquiryId;
    private Integer inquiryAnswerId;
    private String title;
    private String writer;
    private String content;
    private Constants.YN emailReplyYn;
    private LocalDateTime emailReplyAt;
}
