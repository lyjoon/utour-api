package com.utour.mapper;

import com.utour.TestLocalApplication;
import com.utour.entity.Notice;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TestNoticeMapper extends TestLocalApplication {

    @Test
    public void testExists() {
        boolean exists = this.getBean(NoticeMapper.class).exists(Notice.builder().build());
        log.info("exists : {}", exists);
    }

    @Test
    public void testSave() {
        this.getBean(NoticeMapper.class).save(Notice.builder()
                        .title("공지-제목")
                        .content("20톤짜리 화물차 화제")
                        .noticeYn('Y')
                        .writer("홍길동")
                .build());
    }

    @Test
    public void testList() {
        List<Notice> list = this.getBean(NoticeMapper.class).findAll(null);
        log.info("list-size : {}", Optional.ofNullable(list).map(List::size).orElse(0));
    }

    @Test
    public void testSet01() {
        this.testSave();
        this.testSave();
        this.testSave();
        Optional.ofNullable(this.getBean(NoticeMapper.class).findAll(null))
                .ifPresent(list -> list.forEach( item -> this.getBean(NoticeMapper.class).save(item)));
        this.testList();
    }
}
