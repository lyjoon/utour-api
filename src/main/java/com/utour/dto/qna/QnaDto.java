package com.utour.dto.qna;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder @NoArgsConstructor
public class QnaDto {

    private Long qnaId;
    private String title;
    private String content;
    private String writer;
    private Character privateYn;
    private String password;

    private LocalDateTime createAt;

    private int pv;
    private int replyCnt;
}
