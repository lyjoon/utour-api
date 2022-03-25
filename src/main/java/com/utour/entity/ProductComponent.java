package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductComponent extends CommonEntity {

    private String productComponentId;
    private String productId;
    private String componentType;
    private String ordinal;
    private String useYn;
}
