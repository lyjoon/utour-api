package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.board.BoardQueryDto;
import com.utour.entity.Qna;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnaMapper extends CommonMapper<Qna> {

    List<Qna> findPage(BoardQueryDto boardQueryDto);

    Long count(BoardQueryDto boardQueryDto);

    boolean isPermit(@Param(value = "qnaId") Long qnaId, @Param(value = "password") String password);

    void updateIncrementPv(Qna qna);
}