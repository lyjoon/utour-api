package com.utour.dto.product;

import com.utour.dto.common.CommonDto;
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
public class ProductImageDto extends CommonDto {

    private Long productImageId;
    private Long productId;
    private Long productImageGroupId;
    private String imageSrc;
    private String title;
    private String description;

    private String originFileName;
    private boolean deleteYn;
}
