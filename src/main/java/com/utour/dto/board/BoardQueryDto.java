package com.utour.dto.board;

import com.utour.dto.PagingQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BoardQueryDto extends PagingQueryDto {

    private String queryType;
}
