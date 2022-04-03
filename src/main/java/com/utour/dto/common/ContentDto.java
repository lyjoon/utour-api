package com.utour.dto.common;

import com.utour.validator.ValidatorMarkers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ContentDto extends CommonDto{

    @NotEmpty(groups = {ValidatorMarkers.Put.class})
    @Size(max = 50, groups = {ValidatorMarkers.Put.class})
    private String title;

    @NotEmpty(groups = {ValidatorMarkers.Put.class})
    @Size(max = 20, groups = {ValidatorMarkers.Put.class})
    private String writer;

    @NotEmpty(groups = {ValidatorMarkers.Put.class})
    private String content;

}
