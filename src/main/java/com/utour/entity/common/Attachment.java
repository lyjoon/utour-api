package com.utour.entity.common;

import com.utour.common.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public abstract class Attachment extends CommonEntity {

    private Integer attachmentId;
    private String path;
    private String originName;
    private Long size;

}
