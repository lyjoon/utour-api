package com.utour.entity;

import com.utour.entity.common.VwComponent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewComponentAccomm extends VwComponent {

    private Long viewComponentAccommId;
    private Long viewComponentId;
    private String contact;
    private String url;
    private String address;
    private String fax;
}
