package com.utour.dto.qna;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.utour.common.Constants;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QnaDto {

    private Long qnaId;
    private String title;
    private String content;
    private String writer;
    private Character privateYn;
    private String password;

    @JsonFormat(pattern = Constants.PATTERN_ISO_DATETIME)
    private LocalDateTime createAt;

    private int pv;
    private int replyCnt;
}
