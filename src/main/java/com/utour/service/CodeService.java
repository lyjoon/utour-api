package com.utour.service;

import com.utour.common.CommonService;
import com.utour.entity.CodeGroup;
import com.utour.mapper.CodeGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeService extends CommonService {

    // private final CodeMapper codeMapper;
    private final CodeGroupMapper codeGroupMapper;

    @Cacheable(value = "codeGroup", key = "#groupCode")
    public CodeGroup getCodeGroup (String groupCode) {
        return this.codeGroupMapper.findById(CodeGroup.builder().groupCode(groupCode).build());
    }

}
