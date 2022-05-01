package com.utour.dto.notice;

import com.utour.dto.common.AttachDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeAttachDto extends AttachDto {

    private Long noticeId;
    private String cmdType;
}
