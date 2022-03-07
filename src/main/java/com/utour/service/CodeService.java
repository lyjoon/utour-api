package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.code.AreaDto;
import com.utour.dto.code.CodeDto;
import com.utour.dto.code.NationDto;
import com.utour.entity.Area;
import com.utour.entity.CodeGroup;
import com.utour.entity.Nation;
import com.utour.mapper.AreaMapper;
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

    private final CodeGroupMapper codeGroupMapper;
    private final NationMapper nationMapper;
    private final AreaMapper areaMapper;

    @Cacheable(value = "commonCode", key = "#groupCode")
    public List<CodeDto> getCodeList(String groupCode) {
        return Optional.ofNullable(this.codeGroupMapper.findById(CodeGroup.builder().groupCode(groupCode).build()))
                .map(CodeGroup::getCodeList)
                .map(list -> list.stream().map(v -> this.convert(v, CodeDto.class)).collect(Collectors.toList()))
                .orElse(null);
    }

    @Cacheable(value = "areaList", key = "#nationCode")
    public List<AreaDto> getAreaList(String nationCode) {
        return this.areaMapper.findAll(Area.builder().nationCode(nationCode).useYn(Constants.Y).build())
                .stream()
                .map(v -> this.convert(v, AreaDto.class))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "nationList")
    public List<NationDto> getNationList() {
        return this.nationMapper.findAll(Nation.builder().useYn(Constants.Y).build())
                .stream()
                .map(v -> this.convert(v, NationDto.class))
                .collect(Collectors.toList());
    }
}
