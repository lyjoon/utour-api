package com.utour.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utour.domain.Pagination;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestPagingDto extends Pagination {

    private String query;
    private String nationCode;
    private String areaCode;
    private LocalDate startAt, endAt;
    private Character useYn;
}
