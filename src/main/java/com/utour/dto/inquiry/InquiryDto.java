package com.utour.dto.inquiry;

import com.utour.dto.common.ContentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class InquiryDto extends ContentDto {

    private Long inquiryId;
    private String contact;
    private String email;
    private String status;
}
