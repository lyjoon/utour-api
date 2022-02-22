package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.RequestPagingDto;
import com.utour.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HotelMapper extends CommonMapper<Hotel> {

    List<Hotel> findPage(RequestPagingDto requestPagingDto);

}
