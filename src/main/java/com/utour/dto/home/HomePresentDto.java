package com.utour.dto.home;

import com.utour.dto.common.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class HomePresentDto extends CommonDto {
    private Long homePresentId;
    private Long productId;
    private Integer ordinalPosition;
    private Character useYn;
}
