package com.utour.entity;

import com.utour.entity.common.Content;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends Content {

    private Long noticeId;
    private Character noticeYn;
    private int pv;

    @Override
    public Long getId() {
        return this.noticeId;
    }
}
