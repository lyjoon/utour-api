package com.utour.entity;

import com.utour.entity.common.Component;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentImages extends Component {

    private Integer seq;
    private String imageSrc;
}
