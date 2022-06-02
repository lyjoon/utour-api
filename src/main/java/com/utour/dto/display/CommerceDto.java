package com.utour.dto.display;

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
public class CommerceDto extends ProductDto {
    private Long commerceId;
    private Long productId;
    private Integer ordinalPosition;
    private Character useYn;

}
