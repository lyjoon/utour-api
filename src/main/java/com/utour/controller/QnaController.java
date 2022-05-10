package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.qna.QnaDto;
import com.utour.dto.qna.QnaReplyDto;
import com.utour.service.QnaService;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/qna", produces = MediaType.APPLICATION_JSON_VALUE)
public class QnaController extends CommonController {

    private final QnaService qnaService;

    @PutMapping
    public ResultDto<Void> save(@Validated(ValidatorMarkers.Put.class) @Valid @RequestBody QnaDto qnaDto) {
        this.qnaService.save(qnaDto);
        return this.ok(Constants.SUCCESS);
    }

    @GetMapping({"/list", "/page-list"})
    public PaginationResultDto getQnaList(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String queryType,
            @RequestParam(required = false) String query) {
        return this.qnaService.getQnaList(BoardQueryDto.builder()
                .page(page)
                .query(query)
                .queryType(queryType)
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .build());
    }

    @GetMapping("/{qnaId}")
    public ResultDto<QnaDto> get(
            @PathVariable Long qnaId,
            @RequestParam(required = false) String password,
            @RequestHeader(value="Authorization") String authorization
            ) {
        return this.ok(this.qnaService.get(qnaId, password, authorization));
    }

    @GetMapping("/access/{qnaId}")
    public ResultDto<Boolean> access(@PathVariable Long qnaId, @RequestParam(required = false) String password) {
        return this.ok(this.qnaService.isAccess(qnaId, password));
    }


    @GetMapping("/access/{qnaId}/reply/{qnaReplyId}")
    public ResultDto<Boolean> access(@PathVariable Long qnaId, @PathVariable Long qnaReplyId, @RequestParam(required = false) String password) {
        return this.ok(this.qnaService.isAccess(qnaId, qnaReplyId, password));
    }

    @DeleteMapping("/{qnaId}")
    public ResultDto<Void> delete(@PathVariable Long qnaId, @RequestParam(required = false) String password, @RequestHeader(value="Authorization") String authorization) {
        this.qnaService.delete(qnaId, password, authorization);
        return this.ok(Constants.SUCCESS);
    }

    @GetMapping(value = "/{qnaId}/replies")
    public PaginationResultDto getReplies(
            @PathVariable Long qnaId,
            @RequestParam(required = false, defaultValue = "1") Integer page) {
        return this.qnaService.getReplies(qnaId, page);
    }

    @DeleteMapping(value = "/{qnaId}/reply/{qnaReplyId}")
    public ResultDto<Void> delete(@PathVariable Long qnaId, @PathVariable Long qnaReplyId, @RequestParam(required = false) String password, @RequestHeader(value="Authorization") String authorization) {
        QnaReplyDto qnaReplyDto = QnaReplyDto.builder()
                .qnaId(qnaId)
                .qnaReplyId(qnaReplyId)
                .password(password)
                .build();
        this.qnaService.delete(qnaReplyDto, authorization);
        return this.ok(Constants.SUCCESS);
    }

    @PutMapping(value = "/reply")
    public ResultDto<Void> save(@Validated(ValidatorMarkers.Put.class) @RequestBody @Valid QnaReplyDto qnaReplyDto) {
        this.qnaService.save(qnaReplyDto);
        return this.ok(Constants.SUCCESS);
    }
}
