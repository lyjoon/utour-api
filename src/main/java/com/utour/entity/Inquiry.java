package com.utour.entity;

import com.utour.entity.common.Content;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends Content {

    private Long inquiryId;
    private String contact;
    private String email;
    private String status;

    @Override
    public Long getId() {
        return inquiryId;
    }
}
