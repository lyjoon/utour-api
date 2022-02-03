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
public class InquiryAttach extends Attachment {

    private Long inquiryId;
    private Integer inquiryAttachId;
}
