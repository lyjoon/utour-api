package com.utour.mapper;

import com.utour.annotation.Decrypt;
import com.utour.annotation.Encrypt;
import com.utour.common.CommonMapper;
import com.utour.entity.Qna;
import com.utour.entity.QnaReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaReplyMapper extends CommonMapper<QnaReply> {

    @Encrypt
    @Override
    void save(QnaReply qnaReply);

    @Decrypt
    @Override
    QnaReply findById(QnaReply qnaReply);

    Integer count(QnaReply qnaReply);

    Long deleteById(QnaReply qnaReply);
}
