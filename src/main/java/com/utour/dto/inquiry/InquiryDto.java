package com.utour.dto.inquiry;

import com.utour.dto.common.ContentDto;
import com.utour.validator.ValidatorMarkers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class InquiryDto extends ContentDto {

    @NotEmpty(groups = ValidatorMarkers.Update.class)
    private Long inquiryId;

    private String contact;

    @NotEmpty(groups = ValidatorMarkers.Put.class)
    private String email;

    @NotEmpty(groups = ValidatorMarkers.Update.class)
    private String status;

    private Boolean privacy;

    private Boolean thirdParty;
}
