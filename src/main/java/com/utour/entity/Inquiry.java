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
public class Inquiry extends CommonEntity {

    private Long inquiryId;
    private String writer;
    private String content;
    private String contact;
    private String email;
    private String nationCode;
    private String areaCode;
    private Long hotelId;
}
