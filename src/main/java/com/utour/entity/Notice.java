package com.utour.entity;

import com.utour.entity.common.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends Board {

    private Long noticeId;
    private Long noticeYn;

    @Override
    public Long getId() {
        return this.noticeId;
    }
}
