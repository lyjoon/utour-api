package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.code.NationQueryDto;
import com.utour.entity.NationArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NationAreaMapper extends CommonMapper<NationArea> {
    List<NationArea> findPage(NationQueryDto nationQueryDto);
}
