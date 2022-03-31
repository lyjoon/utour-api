package com.utour.entity;

import com.utour.entity.common.Component;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentImage extends Component {

    private String displayType;
}
