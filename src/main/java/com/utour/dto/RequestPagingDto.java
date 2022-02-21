package com.utour.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utour.domain.Pagination;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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
