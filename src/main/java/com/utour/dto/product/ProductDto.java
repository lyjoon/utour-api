package com.utour.dto.product;

import com.utour.dto.common.CommonDto;
import com.utour.dto.view.ViewComponentDto;
import com.utour.validator.ValidatorMarkers;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends CommonDto {

    @NotNull(groups = {ValidatorMarkers.Delete.class})
    private Long productId;
    private String productType;
    private String nationCode;
    private String areaCode;
    private String repImageSrc;
    private Character useYn;
    private String title;
    private String content;
    private String writer;
}