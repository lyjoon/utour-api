package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.PagingQueryDto;
import com.utour.dto.code.*;
import com.utour.entity.*;
import com.utour.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeService extends CommonService {

    private final CodeMapper codeMapper;
    private final CodeGroupMapper codeGroupMapper;
    private final NationMapper nationMapper;
    private final NationAreaMapper nationAreaMapper;

    private final ArrivalMapper arrivalMapper;

    private final AreaMapper areaMapper;

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

    //@Cacheable(value = "nationList")
    public List<NationDto> getNationList() {
        return this.nationMapper.findAll(Nation.builder().useYn(Constants.Y).build())
                .stream()
                .map(v -> this.convert(v, NationDto.class))
                .collect(Collectors.toList());
    }

    //@Cacheable(value = "nation", key = "#nationCode")
    public NationDto getNation(String nationCode) {
        return Optional.ofNullable(this.nationMapper.findById(Nation.builder()
                        .nationCode(nationCode)
                        .useYn(Constants.Y)
                        .build()))
                .map(v -> {
                    NationDto nationDto = this.convert(v, NationDto.class);
                    nationDto.setNationAreaList(this.nationAreaMapper.findAll(NationArea.builder().nationCode(v.getNationCode()).useYn(Constants.Y).build())
                            .stream()
                            .map(v1 -> this.convert(v1, NationAreaDto.class))
                            .collect(Collectors.toList()));
                    return nationDto;
                })
                .orElse(null);
    }

    public List<NationDto> getNationList(NationQueryDto nationQueryDto) {
        return this.nationMapper.findPage(nationQueryDto)
                .stream()
                .map(v -> this.convert(v, NationDto.class))
                .collect(Collectors.toList());
    }

    public NationDto getNation(NationQueryDto nationQueryDto) {
        return Optional.ofNullable(this.nationMapper.findById(Nation.builder()
                        .nationCode(nationQueryDto.getNationCode())
                        .useYn(Constants.Y)
                        .build()))
                .map(v -> {
                    NationDto nationDto = this.convert(v, NationDto.class);
                    nationDto.setNationAreaList(this.nationAreaMapper.findPage(nationQueryDto)
                            .stream()
                            .map(v1 -> this.convert(v1, NationAreaDto.class))
                            .collect(Collectors.toList()));
                    return nationDto;
                })
                .orElse(null);
    }

    public List<ArrivalDto> getList(@NotNull ArrivalDto arrivalDto) {
        return arrivalMapper.findAll(Arrival.builder()
                        .useYn(Optional.ofNullable(arrivalDto.getUseYn()).orElse(Constants.Y))
                        .arrivalCode(arrivalDto.getArrivalCode())
                        .menuYn(arrivalDto.getMenuYn())
                        .build()).stream()
                .map(v -> this.convert(v, ArrivalDto.class))
                .collect(Collectors.toList());
    }

    public List<AreaDto> getList(@NotNull AreaDto areaDto){
        return this.areaMapper.findAll(Area.builder()
                        .areaCode(areaDto.getAreaCode())
                        .arrivalCode(areaDto.getArrivalCode())
                        .nationCode(areaDto.getNationCode())
                        .useYn(areaDto.getUseYn())
                        .menuYn(areaDto.getMenuYn())
                .build()).stream().map(v-> this.convert(v, AreaDto.class))
                .collect(Collectors.toList());
    }

    public ArrivalDto get(ArrivalDto arrivalDto) {
        return Optional.ofNullable(this.arrivalMapper.findById(Arrival.builder().arrivalCode(arrivalDto.getArrivalCode()).build()))
                .map(v -> this.convert(v, ArrivalDto.class)).orElse(null);
    }
}
