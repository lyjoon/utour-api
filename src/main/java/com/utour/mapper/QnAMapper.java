package com.utour.mapper;

import com.utour.annotation.Encrypt;
import com.utour.common.CommonMapper;
import com.utour.entity.QnA;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnAMapper extends CommonMapper<QnA> {

    @Encrypt
    @Override
    void save(QnA qnA);
}