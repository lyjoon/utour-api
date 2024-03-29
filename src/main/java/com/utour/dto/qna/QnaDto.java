package com.utour.dto.qna;

import com.utour.dto.common.ContentDto;
import com.utour.validator.ValidatorMarkers;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class QnaDto extends ContentDto {

    @NotNull(groups = {ValidatorMarkers.Delete.class})
    private Long qnaId;

    private Character privateYn;

    @NotEmpty(groups = {ValidatorMarkers.Put.class, ValidatorMarkers.Delete.class})
    private String password;

    private int pv;
    private int replyCnt;
}
