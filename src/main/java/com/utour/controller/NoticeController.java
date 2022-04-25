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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultDto<Void> save(
            @Valid @RequestPart(value = "notice") @NotNull NoticeDto noticeDto,
            @RequestPart(value = "files", required = false) MultipartFile[] multipartFiles) {
        log.info("notice.save.toString : {}", noticeDto.toString());
        log.info("notice.save.files : {}", multipartFiles != null ? multipartFiles.length : -1);
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
