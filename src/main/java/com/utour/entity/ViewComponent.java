package com.utour.entity;

import com.utour.entity.common.Component;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponent extends Component {

    private Long productId;
    private String viewComponentType;
    private String title;
    private String description;
    private Integer ordinal;
    private Character useYn;
}
