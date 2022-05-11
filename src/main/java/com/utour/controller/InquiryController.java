package com.utour.controller;

import com.utour.annotation.Authorize;
import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.ResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.inquiry.InquiryDto;
import com.utour.exception.ValidException;
import com.utour.service.InquiryService;
import com.utour.validator.ValidatorMarkers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping(value = "/v1/inquiry", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class InquiryController extends CommonController {

    private final InquiryService inquiryService;

    @PutMapping
    public ResultDto<Void> put(@RequestBody @Valid @Validated(ValidatorMarkers.Put.class) InquiryDto inquiryDto) {
        if(!(Optional.ofNullable(inquiryDto.getPrivacy()).orElse(false) &&
                Optional.ofNullable(inquiryDto.getThirdParty()).orElse(false))) {
            // 개인정보활용 약관동의필수
            throw ValidException.builder().message(this.getMessage("error.service.inquiry.terms-not-agreed")).build();
        }
        this.inquiryService.save(inquiryDto);
        return ok();
    }

    @Authorize
    @DeleteMapping("/{inquiryId}")
    public ResultDto<Void> delete(@PathVariable Long inquiryId) {
        this.inquiryService.delete(inquiryId);
        return ok();
    }

    @Authorize
    @GetMapping("/{inquiryId}")
    public ResultDto<InquiryDto> get(@PathVariable Long inquiryId){
        return this.ok(this.inquiryService.get(inquiryId));
    }

    @Authorize
    @GetMapping({"/list", "/page-list"})
    public PaginationResultDto findPage(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String queryType,
            @RequestParam(required = false) String query) {
        return this.inquiryService.getPageList(BoardQueryDto.builder()
                .page(page)
                .query(query)
                .queryType(queryType)
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .build());
    }

    @PutMapping("/status")
    public ResultDto<Void> setStatus(@RequestBody @Valid @Validated(ValidatorMarkers.Update.class) InquiryDto inquiryDto){
        this.inquiryService.updateStatus(inquiryDto);
        return ok();
    }
}
