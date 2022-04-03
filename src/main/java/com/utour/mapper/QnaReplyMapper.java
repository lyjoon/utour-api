package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.entity.QnaReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaReplyMapper extends CommonMapper<QnaReply> {

    Integer count(QnaReply qnaReply);

    Long deleteById(QnaReply qnaReply);

    boolean isPermit(Long qnaId, Long qnaReplyId, String Password);

}
