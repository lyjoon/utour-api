package com.utour.service;

import com.utour.TestLocalApplication;
import com.utour.entity.CodeGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCodeService extends TestLocalApplication {

    @Autowired
    private CodeService codeService;

    @Test
    public void testGetCodeGroup(){
        CodeGroup codeGroup1 = this.codeService.getCodeGroup("PRODUCT_TYPE");
        CodeGroup codeGroup2 = this.codeService.getCodeGroup("PRODUCT_TYPE");

        log.info("{} / {}", codeGroup1.hashCode(), codeGroup2.hashCode());
    }

}
