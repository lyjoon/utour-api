package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.qna.QnaDto;
import com.utour.dto.qna.QnaReplyDto;
import com.utour.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/qna", produces = MediaType.APPLICATION_JSON_VALUE)
public class QnaController extends CommonController {

    private final QnaService qnaService;


    @PutMapping
    public ResultDto<Void> save(@RequestBody QnaDto qnaDto) {
        this.qnaService.save(qnaDto);
        return this.ok(Constants.SUCCESS);
    }

    @GetMapping("/list")
    public PaginationResultDto findPage(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String queryType,
            @RequestParam(required = false) String query) {
        return this.qnaService.findPage(BoardQueryDto.builder()
                .page(page)
                .query(query)
                .queryType(queryType)
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .build());
    }

    @GetMapping("/{qnaId}")
    public ResultDto<QnaDto> findById(@PathVariable Long qnaId) {
        return this.ok(this.qnaService.findById(qnaId));
    }

    @DeleteMapping("/{qnaId}")
    public ResultDto<Void> delete(@PathVariable Long qnaId) {
        this.qnaService.delete(QnaDto.builder().qnaId(qnaId).build());
        return this.ok(Constants.SUCCESS);
    }

    @GetMapping(value = "/{qnaId}/reply/list")
    public ResultDto<List<QnaReplyDto>> findRepliesById(@PathVariable Long qnaId) {
        return this.ok(this.qnaService.findAll(qnaId));
    }

    @DeleteMapping(value = "/{qnaId}/reply/{qnaReplyId}")
    public ResultDto<Void> delete(@PathVariable Long qnaId, @PathVariable Long qnaReplyId) {
        this.qnaService.delete(QnaReplyDto.builder().qnaId(qnaId).qnaReplyId(qnaReplyId).build());
        return this.ok(Constants.SUCCESS);
    }

    @PutMapping(value = "/reply")
    public ResultDto<Void> save(@RequestBody QnaReplyDto qnaReplyDto) {
        this.qnaService.save(qnaReplyDto);
        return this.ok(Constants.SUCCESS);
    }
}
