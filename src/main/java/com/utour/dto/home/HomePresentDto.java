package com.utour.dto.home;

import com.utour.dto.common.CommonDto;
import com.utour.dto.product.ProductDto;
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
public class HomePresentDto extends ProductDto {
    private Long homePresentId;
    private Long productId;
    private Integer ordinalPosition;
    private Character useYn;
}
