package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.common.Constants;
import com.utour.dto.code.AreaDto;
import com.utour.dto.code.CodeDto;
import com.utour.dto.code.NationDto;
import com.utour.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/code")
public class CodeController extends CommonController {

    private final CodeService codeService;

    @GetMapping(value = "/nation")
    public List<NationDto> getNationList(){
        return this.codeService.getNationList();
    }

    @GetMapping(value = "/area/{nationCode}")
    public List<AreaDto> getAreaList(@PathVariable String nationCode){
        return this.codeService.getAreaList(nationCode);
    }

    @GetMapping(value = "/common/{groupCode}")
    public List<CodeDto> getCodeList(@PathVariable String groupCode) {
        return this.codeService.getCodeList(groupCode);
    }

}
