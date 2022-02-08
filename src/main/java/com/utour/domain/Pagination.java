package com.utour.domain;

import com.utour.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Pagination {

    private Integer page;

    private Integer limit;

    public Integer getOffset() {
        return page * Optional.ofNullable(limit).orElse(Constants.DEFAULT_PAGING_COUNT);
    }
}
