package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.dto.code.CodeGroupDto;
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

    @GetMapping(value = "/nation-list")
    public List<NationDto> getNationList(){
        return this.codeService.getNationList();
    }

    @GetMapping(value = "/area/{nationCode}")
    public NationDto getNation(@PathVariable String nationCode){
        return this.codeService.getNation(nationCode);
    }

    @GetMapping(value = "/common/{groupCode}")
    public CodeGroupDto getCode(@PathVariable String groupCode) {
        return this.codeService.getCodeGroup(groupCode);
    }

}
