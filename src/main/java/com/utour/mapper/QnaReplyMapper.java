package com.utour.mapper;

import com.utour.annotation.EncryptValue;
import com.utour.common.CommonMapper;
import com.utour.entity.QnaReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaReplyMapper extends CommonMapper<QnaReply> {

    @Override
    void save(@EncryptValue QnaReply qnaReply);

    Integer count(QnaReply qnaReply);

    Long deleteById(QnaReply qnaReply);
}
