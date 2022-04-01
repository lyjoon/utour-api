package com.utour.dto.qna;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaReplyDto {

    private Long qnaId;
    private Integer qnaReplyId;
    private String writer;
    private String content;
    private String password;
    private Character privateYn;
    private Character adminYn;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createAt;
}
