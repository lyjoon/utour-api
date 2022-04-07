package com.utour.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class CommonEntity {

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
