package com.utour.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 응답결과 + 메세지
 */
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    /**
     * 응답 메세지
     */
    private String message;

    /**
     * 결과 데이터
     */
    private T data;

}
