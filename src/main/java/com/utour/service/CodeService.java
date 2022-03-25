package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.code.CodeDto;
import com.utour.dto.code.CodeGroupDto;
import com.utour.dto.code.NationDto;
import com.utour.entity.Code;
import com.utour.entity.CodeGroup;
import com.utour.entity.Nation;
import com.utour.mapper.CodeMapper;
import com.utour.mapper.CodeGroupMapper;
import com.utour.mapper.NationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeService extends CommonService {

    private final CodeMapper codeMapper;
    private final CodeGroupMapper codeGroupMapper;
    private final NationMapper nationMapper;

    @Cacheable(value = "code", key = "#groupCode")
    public CodeGroupDto getCodeGroup(String groupCode) {
        return Optional.ofNullable(this.codeGroupMapper.findById(CodeGroup.builder().groupCode(groupCode).build()))
                .map(v -> {
                    CodeGroupDto codeGroupDto = this.convert(v, CodeGroupDto.class);
                    codeGroupDto.setCodeList(this.codeMapper.findAll(Code.builder().groupCode(codeGroupDto.getGroupCode()).build()).stream().map(code -> this.convert(code , CodeDto.class)).collect(Collectors.toList()));
                    return codeGroupDto;
                })
                .orElse(null);
    }

    @Cacheable(value = "nation-list")
    public List<NationDto> getNationList() {
        return this.nationMapper.findAll(Nation.builder().useYn(Constants.Y).build())
                .stream()
                .map(v -> this.convert(v, NationDto.class))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "nation", key = "#nationCode")
    public NationDto getNation(String nationCode) {
        return Optional.ofNullable(this.nationMapper.findById(Nation.builder().nationCode(nationCode).useYn(Constants.Y).build()))
                .map(v -> this.convert(v, NationDto.class))
                .orElse(null);
    }
}
