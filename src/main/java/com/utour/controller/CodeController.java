package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.PagingQueryDto;
import com.utour.dto.ResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.code.CodeGroupDto;
import com.utour.dto.code.NationDto;
import com.utour.dto.code.NationQueryDto;
import com.utour.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/code")
public class CodeController extends CommonController {

    private final CodeService codeService;

    @GetMapping(value = "/nation/all")
    public ResultDto<List<NationDto>> getNationList(){
        return this.ok(this.codeService.getNationList());
    }

    @GetMapping(value = "/nation/list")
    public ResultDto<List<NationDto>> getNationList(@RequestParam(required = false, value = "query") String query){
        NationQueryDto nationQueryDto = NationQueryDto.builder()
                .page(1)
                .limit(10)
                .useYn(Constants.Y)
                .query(query)
                .build();

        return this.ok(this.codeService.getNationList(nationQueryDto));
    }

    @GetMapping(value = "/nation")
    public ResultDto<NationDto> getNation(
            @RequestParam(value = "nationCode") String nationCode,
            @RequestParam(required = false, value = "query") String query) {
        NationQueryDto nationQueryDto = NationQueryDto.builder()
                .page(1)
                .limit(10)
                .useYn(Constants.Y)
                .nationCode(nationCode)
                .query(query)
                .build();

        return this.ok(this.codeService.getNation(nationQueryDto));
    }

    @GetMapping(value = "/common")
    public ResultDto<CodeGroupDto> getCode(@RequestParam String groupCode) {
        return this.ok(this.codeService.getCodeGroup(groupCode));
    }

}
