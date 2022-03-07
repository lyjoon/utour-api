package com.utour.service;

import com.utour.TestLocalApplication;
import com.utour.common.Constants;
import com.utour.dto.code.CodeDto;
import com.utour.entity.CodeGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestCodeService extends TestLocalApplication {

    @Autowired
    private CodeService codeService;

    @Test
    public void testGetCodeGroup(){
        List<CodeDto> codeGroup1 = this.codeService.getCodeList(Constants.GroupCode.PRODUCT_TYPE.name());
        List<CodeDto> codeGroup2 = this.codeService.getCodeList(Constants.GroupCode.PRODUCT_TYPE.name());

        log.info("{} / {}", codeGroup1.hashCode(), codeGroup2.hashCode());
    }

    @Test
    public void testEnumValueOf(){
        log.info("{}", Constants.GroupCode.valueOf("productType"));
    }

}
