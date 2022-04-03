package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.board.BoardQueryDto;
import com.utour.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper extends CommonMapper<Notice> {

    List<Notice> findPage(BoardQueryDto boardQueryDto);

    Long count(BoardQueryDto boardQueryDto);

}
