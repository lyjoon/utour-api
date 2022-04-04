package com.utour.dto;

import com.utour.domain.Pagination;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PaginationQueryDto extends Pagination {

    private String query;
    private LocalDate startAt, endAt;
    private Character useYn;
}
