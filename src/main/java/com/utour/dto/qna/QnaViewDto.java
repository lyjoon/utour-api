package com.utour.dto.qna;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class QnaViewDto {

    private QnaDto qnaDto;
    private boolean access;
    private boolean exists;
}
