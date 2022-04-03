package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.notice.NoticeDto;
import com.utour.entity.Notice;
import com.utour.mapper.NoticeAttachMapper;
import com.utour.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService extends CommonService {

    private final NoticeMapper noticeMapper;
    private final NoticeAttachMapper noticeAttachMapper;

    public NoticeDto get(Long noticeId) {
        return Optional.ofNullable(noticeMapper.findById(Notice.builder().noticeId(noticeId).build()))
                .map(notice -> this.convert(notice, NoticeDto.class))
                .orElse(null);
    }

    public void save(NoticeDto noticeDto) {
        this.noticeMapper.save(Notice.builder()
                .noticeId(noticeDto.getNoticeId())
                .content(noticeDto.getContent())
                .noticeYn(noticeDto.getNoticeYn())
                .pv(noticeDto.getPv())
                .title(noticeDto.getTitle())
                .writer(noticeDto.getWriter())
                .build());
    }

    public void delete(Long noticeId) {
        this.noticeMapper.delete(Notice.builder()
                .noticeId(noticeId)
                .build());
    }

    public PaginationResultDto getPageList(BoardQueryDto boardQueryDto) {
        long count = this.noticeMapper.count(boardQueryDto);
        List<Notice> list =this.noticeMapper.findPage(boardQueryDto);
        return PaginationResultDto.builder()
                .count(count)
                .page(boardQueryDto.getPage())
                .limit(boardQueryDto.getLimit())
                .result(list.stream().map(v -> this.convert(v, NoticeDto.class)).collect(Collectors.toList()))
                .build();
    }
}
