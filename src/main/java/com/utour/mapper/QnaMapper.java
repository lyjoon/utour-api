package com.utour.mapper;

import com.utour.annotation.EncryptValue;
import com.utour.common.CommonMapper;
import com.utour.dto.board.BoardQueryDto;
import com.utour.entity.Qna;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnaMapper extends CommonMapper<Qna> {


    @Override
    void save(Qna qna);

    List<Qna> findPage(BoardQueryDto boardQueryDto);

    Long count(BoardQueryDto boardQueryDto);

    boolean matched(@Param(value = "qnaId") Long qnaId, @EncryptValue @Param(value = "password") String password);
}