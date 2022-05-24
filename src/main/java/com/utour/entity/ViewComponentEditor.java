package com.utour.entity;

import com.utour.entity.common.Component;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentEditor extends Component {

    private String content;

}
