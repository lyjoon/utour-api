package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponent extends CommonEntity {

    private Long viewComponentId;
    private String viewComponentType;
    private Long productId;
    private Integer ordinal;
    private Character useYn;
}
