package com.utour.service;

import com.utour.TestLocalApplication;
import com.utour.common.Constants;
import com.utour.dto.qna.QnaDto;
import com.utour.entity.Qna;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TestQnaService extends TestLocalApplication {

    @Autowired
    public QnaService qnaService;

    @Test
    public void save(){

        QnaDto qnaDto = QnaDto.builder()
                .title("제목")
                .content("컨텐츠")
                .writer("test")
                .password("5096")
                .privateYn(Constants.N)
                .build();

        /*Qna qna = new ModelMapper().map(qnaDto, Qna.class);
        log.info("{}", qna);

        QnaDto qnaDto1 = new ModelMapper().map(Qna.builder()
                .title("제목")
                .content("컨텐츠")
                .writer("test")
                .password("5096")
                .privateYn(Constants.N)
                .build(), QnaDto.class);

        log.info("{}", qnaDto1);*/
        this.qnaService.save(qnaDto);
    }
}
