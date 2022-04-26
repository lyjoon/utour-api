package com.utour.mapper;

import com.utour.TestLocalApplication;
import com.utour.dto.board.BoardQueryDto;
import com.utour.entity.Inquiry;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestInquiryMapper extends TestLocalApplication {

    @Autowired
    private InquiryMapper inquiryMapper;


    @Test
    public void testFind(){
        Inquiry inquiry = this.inquiryMapper.findById(Inquiry.builder().inquiryId(1L).build());
        log.info("{}", inquiry);
    }
    @Test
    public void testFindPage(){
        List<Inquiry> inquiry = this.inquiryMapper.findPage(BoardQueryDto.builder().page(1).limit(10).build());
        log.info("{}", inquiry);
    }
}
