package com.utour.mapper;

import com.utour.TestMapper;
import com.utour.entity.Qna;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TestQnaMapper extends TestMapper {

    @Autowired
    QnaMapper qnAMapper;

    @Test
    @Override
    public void exists() {
        this.save();
        Boolean exists = this.qnAMapper.exists(Qna.builder().qnaId(1L).build());
        Assertions.assertNotNull(exists);
    }

    @Test
    @Override
    public void findById() {
        this.save();
        Qna qnA = qnAMapper.findById(Qna.builder().qnaId(1L).build());
        log.info("{}", qnA.toString());
        Assertions.assertNotNull(qnA);
    }

    @Test
    @Override
    public void findAll() {
        this.save();
        this.save();
        List<Qna> list = this.qnAMapper.findAll(null);
        log.info("list-size : {}", Optional.ofNullable(list).map(List::size).orElse(0));
        Assertions.assertNotNull(list);
    }

    @Test
    @Override
    public void save() {
        this.qnAMapper.save(Qna.builder()
                .password("1234")
                .title("고래밥")
                .content("20톤짜리 화물차 화제")
                .writer("HGD")
                .pv(11)
                .build());
    }

    @Test
    @Override
    public void delete() {
        this.save();
        long cnt = this.qnAMapper.delete(Qna.builder().qnaId(1L).build());
        log.info("delete-count : {}", cnt);
        Assertions.assertNotEquals(cnt, 0L);
    }

    @Test
    public void testMatch(){
        this.save();
        List<Qna> list = this.qnAMapper.findAll(null);
        boolean flag = this.qnAMapper.isAccess(1L, "1234");
        log.info("is-matched : {}", flag);
    }
}
