package com.utour.entity;

import com.utour.entity.common.Component;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentFacility extends Component {

    private Integer viewComponentSeq;
    private String iconType;
    private String title;
    private String description;

}
