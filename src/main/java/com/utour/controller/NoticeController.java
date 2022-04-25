package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.notice.NoticeDto;
import com.utour.service.NoticeService;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController extends CommonController {

    private final NoticeService noticeService;

    @GetMapping("/{noticeId}")
    public ResultDto<NoticeDto> get(@PathVariable Long noticeId) {
        return this.ok(this.noticeService.get(noticeId));
    }

    @Authorize
    @DeleteMapping("/{noticeId}")
    public ResultDto<Void> delete(@PathVariable Long noticeId) {
        this.noticeService.delete(noticeId);
        return ok();
    }

    @Authorize
    @PostMapping
    public ResultDto<Void> save(@Valid @Validated(value = ValidatorMarkers.Put.class) @RequestBody NoticeDto noticeDto) {
        this.noticeService.save(noticeDto);
        return ok();
    }

    @GetMapping({"/list", "/page-list"})
    public PaginationResultDto getPageList(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String queryType,
            @RequestParam(required = false) String query
    ){
        return this.noticeService.getPageList(BoardQueryDto.builder()
                .page(page)
                .query(query)
                .queryType(queryType)
                .build());
    }

}
