package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.code.NationQueryDto;
import com.utour.entity.NationArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @deprecated 미사용 개체
 */
@Mapper
@Deprecated
public interface NationAreaMapper extends CommonMapper<NationArea> {
    List<NationArea> findPage(NationQueryDto nationQueryDto);
}
