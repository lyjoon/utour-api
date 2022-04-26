package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.board.BoardQueryDto;
import com.utour.entity.Inquiry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper extends CommonMapper<Inquiry> {

    List<Inquiry> findPage(BoardQueryDto boardQueryDto);

    long count(BoardQueryDto boardQueryDto);
}
