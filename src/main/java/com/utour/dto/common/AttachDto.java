package com.utour.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class AttachDto extends CommonDto {

    private Long attachId;
    @JsonIgnore
    private String path;
    private String originName;
    private Long size;
}
