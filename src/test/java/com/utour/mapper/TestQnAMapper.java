package com.utour.mapper;

import com.utour.TestMapper;
import com.utour.common.Constants;
import com.utour.entity.Notice;
import com.utour.entity.QnA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TestQnAMapper extends TestMapper {

    @Autowired
    QnAMapper qnAMapper;

    @Test
    @Override
    public void exists() {
        this.save();
        Boolean exists = this.qnAMapper.exists(QnA.builder().qnaId(1L).build());
        Assertions.assertNotNull(exists);
    }

    @Test
    @Override
    public void findById() {
        this.save();
        QnA qnA = qnAMapper.findById(QnA.builder().qnaId(1L).build());
        log.info("{}", qnA.toString());
        Assertions.assertNotNull(qnA);
    }

    @Test
    @Override
    public void findAll() {
        List<QnA> list = this.qnAMapper.findAll(null);
        log.info("list-size : {}", Optional.ofNullable(list).map(List::size).orElse(0));
        Assertions.assertNotNull(list);
    }

    @Test
    @Override
    public void save() {
        this.qnAMapper.save(QnA.builder()
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
        long cnt = this.qnAMapper.delete(QnA.builder().qnaId(1L).build());
        log.info("delete-count : {}", cnt);
        Assertions.assertNotEquals(cnt, 0L);
    }
}
