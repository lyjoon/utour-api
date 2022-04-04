package com.utour.entity.common;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public abstract class Attach extends CommonEntity {

    private Long attachId;
    private String path;
    private String originName;
    private Long size;

}
