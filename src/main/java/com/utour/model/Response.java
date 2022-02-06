package com.utour.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * 응답결과 + 메세지
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> extends Pagination {

    /**
     * 응답 메세지
     */
    private String message;

    /**
     * 결과 데이터
     */
    private T data;

}
