package com.utour.entity;

import com.utour.entity.common.Board;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends Board {

    private Long noticeId;
    private Character noticeYn;

    @Override
    public Long getId() {
        return this.noticeId;
    }
}
