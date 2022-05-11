package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PagingQueryDto;
import com.utour.dto.PagingResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/user/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends CommonController {

    private final UserService userService;

    @GetMapping({"/list", "/page-list"})
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
}
