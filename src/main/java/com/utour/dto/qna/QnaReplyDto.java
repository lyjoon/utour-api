package com.utour.dto.qna;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utour.common.Constants;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaReplyDto {

    private Long qnaId;
    private Long qnaReplyId;
    private String writer;
    private String content;
    private String password;
    private Character privateYn;
    private Character adminYn;

    @JsonFormat(pattern = Constants.PATTERN_ISO_DATETIME)
    private LocalDateTime createAt;
}
