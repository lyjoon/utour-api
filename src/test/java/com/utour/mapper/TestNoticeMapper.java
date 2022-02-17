package com.utour.mapper;

import com.utour.TestMapper;
import com.utour.common.Constants;
import com.utour.entity.Notice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TestNoticeMapper extends TestMapper {

    @Autowired
    NoticeMapper noticeMapper;

    @Test
    @Override
    public void exists() {
        Boolean exists = this.getBean(NoticeMapper.class).exists(Notice.builder().build());
        Assertions.assertNotNull(exists);
    }

    @Test
    @Override
    public void findById() {
        this.save();
        Notice notice = noticeMapper.findById(Notice.builder().noticeId(1L).build());
        log.info("{}", notice.toString());
        Assertions.assertNotNull(notice);
    }

    @Test
    @Override
    public void findAll() {
        Optional.ofNullable(this.getBean(NoticeMapper.class))
                .ifPresent(mapper -> {
                    List<Notice> list = this.getBean(NoticeMapper.class).findAll(null);
                    log.info("list-size : {}", Optional.ofNullable(list).map(List::size).orElse(0));
                    Assertions.assertNotNull(list);
                });
    }

    @Test
    @Override
    public void save() {
        this.getBean(NoticeMapper.class).save(Notice.builder()
                .title("공지-제목")
                .content("20톤짜리 화물차 화제")
                .noticeYn(Constants.Y)
                .writer("홍길동")
                .build());
    }

    @Test
    @Override
    public void delete() {
        this.save();
        Optional.ofNullable(this.getBean(NoticeMapper.class)).ifPresent(mapper -> {
            mapper.findAll(null).forEach(t -> mapper.delete(t));
        });
    }
}
