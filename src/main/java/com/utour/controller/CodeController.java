package com.utour.controller;

import com.utour.common.CommonController;
import com.utour.dto.code.CodeGroupDto;
import com.utour.dto.code.NationDto;
import com.utour.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/area")
    public NationDto getNation(@RequestParam String nationCode){
        return this.codeService.getNation(nationCode);
    }

    @GetMapping(value = "/common")
    public CodeGroupDto getCode(@RequestParam String groupCode) {
        return this.codeService.getCodeGroup(groupCode);
    }

}
