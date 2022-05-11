package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PagingQueryDto;
import com.utour.dto.PagingResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.user.UserDto;
import com.utour.service.UserService;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends CommonController {

    private final UserService userService;

    @Authorize
    @GetMapping(value = "/list")
    public PagingResultDto getPageList(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Character useYn
    ){
        return this.userService.getPageList(
                PagingQueryDto.builder()
                        .page(page)
                        .query(query)
                        .useYn(useYn)
                        .limit(Constants.DEFAULT_PAGING_COUNT)
                        .build());
    }


    @Authorize
    @GetMapping
    public ResultDto<UserDto> save(@RequestParam(value = "userId") java.lang.String userId) {
        return this.ok(this.userService.get(userId));
    }

    @Authorize
    @PutMapping
    public ResultDto<Void> save(@RequestBody @Valid @Validated(ValidatorMarkers.Put.class) UserDto userDto) {
        this.userService.save(userDto);
        return this.ok();
    }


    @Authorize
    @DeleteMapping
    public ResultDto<Void> delete(@RequestParam(value = "userId") java.lang.String userId) {
        this.userService.delete(userId);
        return this.ok(Constants.SUCCESS);
    }

    @Authorize
    @PutMapping(value = "updateYn")
    public ResultDto<Void> updateYn(@RequestBody @Valid @Validated(ValidatorMarkers.Update.class) UserDto userDto) {
        this.userService.updateYn(userDto);
        return this.ok(Constants.SUCCESS);
    }

}
