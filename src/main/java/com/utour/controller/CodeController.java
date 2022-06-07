package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.dto.code.*;
import com.utour.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/code")
public class CodeController extends CommonController {

    private final CodeService codeService;

    @PostMapping(value = "/arrival-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArrivalDto>> getArrivalList(@RequestBody ArrivalDto arrivalDto) {
        return this.response(this.codeService.getList(arrivalDto));
    }

    @PostMapping(value = "/area-list")
    public ResponseEntity<List<AreaDto>> getAreaList(@RequestBody AreaDto areaDto) {
        return this.response(this.codeService.getList(areaDto));
    }

    @GetMapping(value = "/common")
    public ResponseEntity<CodeGroupDto> getCode(@RequestParam String groupCode) {
        return this.response(this.codeService.getCodeGroup(groupCode));
    }

}
