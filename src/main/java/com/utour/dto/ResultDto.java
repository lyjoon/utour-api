package com.utour.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 응답결과 + 메세지
 */
@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto<T> {

    /**
     * 응답 메세지
     */
    private String message;

    /**
     * 결과 데이터
     */
    private T result;

}
