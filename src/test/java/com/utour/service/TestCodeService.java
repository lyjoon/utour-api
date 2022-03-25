package com.utour.service;

import com.utour.TestLocalApplication;
import com.utour.common.Constants;
import com.utour.dto.code.CodeDto;
import com.utour.dto.code.CodeGroupDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCodeService extends TestLocalApplication {

    @Autowired
    private CodeService codeService;

    @Test
    public void testGetCodeGroup(){
        CodeGroupDto codeGroup1 = this.codeService.getCodeGroup(Constants.GroupCode.PRODUCT_TYPE.name());
        CodeGroupDto codeGroup2 = this.codeService.getCodeGroup(Constants.GroupCode.PRODUCT_TYPE.name());

        log.info("{} / {}", codeGroup1.hashCode(), codeGroup2.hashCode());
    }

    @Test
    public void testEnumValueOf(){
        log.info("{}", Constants.GroupCode.valueOf("productType"));
    }

}
