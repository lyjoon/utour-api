package com.utour.entity;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImageGroup extends CommonEntity {

    private Long productImageGroupId;
    private Long productId;
    private String groupName;
    private Character useYn;

}
