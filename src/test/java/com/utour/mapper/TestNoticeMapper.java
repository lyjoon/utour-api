package com.utour.mapper;

import com.utour.TestLocalApplication;
import com.utour.entity.Notice;
import org.junit.jupiter.api.Test;

public class TestNoticeMapper extends TestLocalApplication {

    @Test
    public void testExists() {
        boolean exists = this.getBean(NoticeMapper.class).exists(Notice.builder().build());
        log.info("exists : {}", exists);
    }
}
