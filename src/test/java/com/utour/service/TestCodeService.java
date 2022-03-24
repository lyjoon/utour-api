package com.utour.service;

import com.utour.TestLocalApplication;
import com.utour.common.Constants;
import com.utour.dto.code.CodeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestCodeService extends TestLocalApplication {

    @Autowired
    private CodeService codeService;

    @Test
    public void testEnumValueOf(){
        log.info("{}", Constants.GroupCode.valueOf("productType"));
    }

}
