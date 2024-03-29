package com.utour.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResultDto extends ResultDto<List> {

    private Integer page;
    private Integer limit;
    private Long count;

    public int getPageCount(){
        return Optional.ofNullable(count)
                .map(n -> (int) Math.ceil((double) n / getLimit()))
                .orElse(0);
    }

}
