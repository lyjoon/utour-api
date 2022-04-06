package com.utour.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ViewComponentImageDto extends ViewComponentDto{

    private String displayType;

    private List<ViewComponentImageSetDto> imagesList;
}
