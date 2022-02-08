package com.utour.entity;

import com.utour.common.Constants;
import com.utour.entity.common.Content;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends Content {

    private Long noticeId;
    private Constants.YN noticeYn;

    @Override
    public Long getId() {
        return this.noticeId;
    }
}
