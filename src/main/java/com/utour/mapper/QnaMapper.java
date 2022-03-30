package com.utour.mapper;

import com.utour.annotation.Decrypt;
import com.utour.annotation.Encrypt;
import com.utour.common.CommonMapper;
import com.utour.dto.board.BoardQueryDto;
import com.utour.entity.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper extends CommonMapper<Qna> {

    @Encrypt
    @Override
    void save(Qna qna);

    @Decrypt
    @Override
    Qna findById(Qna qnA);

    List<Qna> findPage(BoardQueryDto boardQueryDto);
}