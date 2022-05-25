package com.utour.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utour.dto.common.CommonDto;
import com.utour.validator.ValidatorMarkers;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

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
    private String nationName;
    private String areaCode;
    private String areaName;
    private String repImageSrc;
    @JsonIgnore
    private String repImagePath;
    private Character useYn;
    private String title;
    private String content;
    private String writer;
}