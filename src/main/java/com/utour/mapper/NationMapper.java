package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.PagingQueryDto;
import com.utour.dto.code.NationQueryDto;
import com.utour.entity.Nation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NationMapper extends CommonMapper<Nation> {
    List<Nation> findPage(NationQueryDto nationQueryDto);
}
