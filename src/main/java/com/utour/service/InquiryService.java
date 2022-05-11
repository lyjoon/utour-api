package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.PagingResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.inquiry.InquiryDto;
import com.utour.entity.Inquiry;
import com.utour.exception.InternalException;
import com.utour.mapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService extends CommonService {

    private final InquiryMapper inquiryMapper;

    public void save(InquiryDto inquiryDto) {

        Inquiry inquiry = Inquiry.builder()
                .inquiryId(inquiryDto.getInquiryId())
                .contact(inquiryDto.getContact())
                .email(inquiryDto.getEmail())
                .status(Optional.ofNullable(inquiryDto.getStatus()).orElse(Constants.InquiryStatus.WAIT.name()))
                .content(inquiryDto.getContent())
                .title(inquiryDto.getTitle())
                .build();

        this.inquiryMapper.save(inquiry);
    }


    public void updateStatus(InquiryDto inquiryDto){
        Inquiry inquiry = Inquiry.builder()
                .inquiryId(inquiryDto.getInquiryId())
                .status(Optional.ofNullable(inquiryDto.getStatus()).orElse(Constants.InquiryStatus.WAIT.name()))
                .build();
        this.inquiryMapper.updateStatus(inquiry);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long inquiryId) {
        Inquiry inquiry = Inquiry.builder().inquiryId(inquiryId).build();
        boolean exists = Optional.ofNullable(this.inquiryMapper.exists(inquiry)).orElse(false);
        if(!exists) {
            throw new InternalException(this.getMessage("error.service.inquiry.not-exists"));
        }
        this.inquiryMapper.delete(inquiry);
    }

    public PagingResultDto getPageList(BoardQueryDto boardQueryDto) {
        long count = this.inquiryMapper.count(boardQueryDto);
        List<InquiryDto> results = this.inquiryMapper.findPage(boardQueryDto).stream()
                .map(v -> this.convert(v, InquiryDto.class))
                .collect(Collectors.toList());

        return PagingResultDto.builder()
                .page(boardQueryDto.getPage())
                .limit(boardQueryDto.getLimit())
                .count(count)
                .result(results)
                .build();
    }

    public InquiryDto get(Long inquiryId) {
        return Optional.ofNullable(this.inquiryMapper.findById(Inquiry.builder().inquiryId(inquiryId).build()))
                .map(v -> this.convert(v, InquiryDto.class))
                .orElse(null);
    }
}
