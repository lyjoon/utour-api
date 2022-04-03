package com.utour.dto.qna;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utour.common.Constants;
import com.utour.validator.ValidatorMarkers;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaReplyDto {

    @NotNull(groups = {ValidatorMarkers.Delete.class})
    private Long qnaId;
    @NotNull(groups = {ValidatorMarkers.Delete.class})
    private Long qnaReplyId;
    @Size(max = 20, groups = {ValidatorMarkers.Put.class})
    private String writer;
    @NotEmpty(groups = {ValidatorMarkers.Put.class})
    private String content;
    @NotEmpty(groups = {ValidatorMarkers.Put.class, ValidatorMarkers.Delete.class})
    private String password;
    private Character privateYn;
    private Character adminYn;

    private LocalDateTime createAt;
}
