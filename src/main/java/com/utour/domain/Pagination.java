package com.utour.domain;

import com.utour.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Pagination {
    @Getter
    private Integer page;
    private Integer limit;

    public Integer getOffset() {
        Integer offset = Optional.ofNullable(this.page).map(i -> (i > 0 ? i -1 : i) * this.getLimit()).orElse(null);
        return offset;
    }

    public Integer getLimit() {
        int limit = Optional.ofNullable(this.limit).orElse(Constants.DEFAULT_PAGING_COUNT);
        return limit;
    }
}
