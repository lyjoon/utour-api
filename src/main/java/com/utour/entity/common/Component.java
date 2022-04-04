package com.utour.entity.common;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class Component extends CommonEntity {

    private Long viewComponentId;

}
