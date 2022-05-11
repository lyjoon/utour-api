package com.utour.dto.user;

import com.utour.dto.common.CommonDto;
import com.utour.validator.ValidatorMarkers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto extends CommonDto {

    @NotEmpty(groups = {ValidatorMarkers.Put.class, ValidatorMarkers.Update.class})
    private String userId;
    private Character useYn;
    private String password;
    private String name;
}
