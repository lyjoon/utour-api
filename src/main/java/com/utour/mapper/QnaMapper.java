package com.utour.mapper;

import com.utour.annotation.Decrypt;
import com.utour.annotation.Encrypt;
import com.utour.common.CommonMapper;
import com.utour.entity.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper extends CommonMapper<Qna> {

    @Encrypt
    @Override
    void save(Qna qnA);

    @Decrypt
    @Override
    Qna findById(Qna qnA);

    List<Qna> findAll(Qna qnA);
}