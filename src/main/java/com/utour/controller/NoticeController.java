package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.notice.NoticeAttachDto;
import com.utour.dto.notice.NoticeDto;
import com.utour.dto.notice.NoticeViewDto;
import com.utour.service.NoticeService;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/v1/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController extends CommonController {

    private final NoticeService noticeService;

    @GetMapping("/{noticeId}")
    public ResultDto<NoticeViewDto> get(@PathVariable Long noticeId) {
        return this.ok(this.noticeService.get(noticeId));
    }

    @Authorize
    @DeleteMapping("/{noticeId}")
    public ResultDto<Void> delete(@PathVariable Long noticeId) {
        this.noticeService.delete(noticeId);
        return ok();
    }

    @Authorize
    @PostMapping(value = "/save", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultDto<Void>> save(
            @Valid @RequestPart(value = "notice") @NotNull NoticeDto noticeDto,
            @RequestPart(value = "attachments", required = false) MultipartFile[] attachments
    ) {
        this.noticeService.save(noticeDto, attachments);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.ok());
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
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .build());
    }

}
