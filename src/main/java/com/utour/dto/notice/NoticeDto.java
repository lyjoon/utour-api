package com.utour.dto.notice;

import com.utour.dto.common.ContentDto;
import com.utour.validator.ValidatorMarkers;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto extends ContentDto {

    @NotNull(groups = {ValidatorMarkers.Delete.class})
    private Long noticeId;

    private Character noticeYn;

    private int pv;

    private boolean attachment;
}
