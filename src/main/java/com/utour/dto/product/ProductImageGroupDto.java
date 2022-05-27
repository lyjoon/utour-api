package com.utour.dto.product;

import com.utour.dto.common.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageGroupDto extends CommonDto {

    private Long productImageGroupId;
    private Long productId;
    private String groupName;
    private Character useYn;

    private List<ProductImageDto> productImages;
    private boolean deleteYn;
}
