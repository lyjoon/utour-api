package com.utour.mapper;

import com.utour.TestMapper;
import com.utour.common.Constants;
import com.utour.entity.Code;
import com.utour.entity.CodeGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCodeMapper extends TestMapper {

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private CodeGroupMapper codeGroupMapper;

    @Test
    @Override
    public void exists() {
        boolean exists = this.codeMapper.exists(Code.builder().groupCode(Constants.GroupCode.PRODUCT_TYPE.name()).build());
        log.info("exists : {}", exists);
    }

    @Test
    @Override
    public void findById() {
        //this.save();
        CodeGroup codeGroup = this.codeGroupMapper.findById(CodeGroup.builder().groupCode(Constants.GroupCode.PRODUCT_TYPE.name()).build());
        log.info("findById : {}", codeGroup.toString());
    }

    @Test
    @Override
    public void findAll() {
        this.codeMapper.findAll(Code.builder().groupCode(Constants.GroupCode.PRODUCT_TYPE.name()).build()).forEach(code ->
                log.info(":{}", code.toString()));
    }

    @Test
    @Override
    public void save() {
    }

    @Test
    @Override
    public void delete() {

    }
}
