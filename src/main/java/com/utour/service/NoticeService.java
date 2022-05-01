package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.notice.NoticeDto;
import com.utour.entity.Notice;
import com.utour.entity.NoticeAttach;
import com.utour.mapper.NoticeAttachMapper;
import com.utour.mapper.NoticeMapper;
import com.utour.util.ErrorUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService extends CommonService {

    private final NoticeMapper noticeMapper;
    private final NoticeAttachMapper noticeAttachMapper;

    @Value(value = "${app.file-upload-storage.notice:}")
    private Path fileStoragePath;

    public NoticeDto get(Long noticeId) {
        return Optional.ofNullable(noticeMapper.findById(Notice.builder().noticeId(noticeId).build()))
                .map(notice -> this.convert(notice, NoticeDto.class))
                .orElse(null);
    }

    public void save(NoticeDto noticeDto, MultipartFile[] attachments) {
        Notice notice = Notice.builder()
                .noticeId(noticeDto.getNoticeId())
                .content(noticeDto.getContent())
                .noticeYn(noticeDto.getNoticeYn())
                .pv(noticeDto.getPv())
                .title(noticeDto.getTitle())
                .writer(noticeDto.getWriter())
                .build();
        this.noticeMapper.save(notice);

        // cmdType 에 따른 기존파일삭제
        // 신규파일저장
        if(attachments != null && attachments.length > 0) {
            for (MultipartFile multipartFile : attachments) {
                try {
                    String originName = multipartFile.getOriginalFilename();
                    String fileName = new StringBuilder(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                            .append("-")
                            .append(UUID.randomUUID())
                            .append("-")
                            .append(originName)
                            .toString();
                    Path toPath = Paths.get(fileStoragePath.toFile().getPath(), fileName);
                    multipartFile.transferTo(toPath);
                    NoticeAttach noticeAttach = NoticeAttach.builder()
                            .noticeId(notice.getNoticeId())
                            .originName(multipartFile.getOriginalFilename())
                            .size(multipartFile.getSize())
                            .path(toPath.toFile().getPath())
                            .build();
                    this.noticeAttachMapper.save(noticeAttach);
                } catch (IOException ioException) {
                    log.error("{}", ErrorUtils.throwableInfo(ioException));
                }
            }
        }
    }

    public void delete(Long noticeId) {
        this.noticeMapper.delete(Notice.builder()
                .noticeId(noticeId)
                .build());
    }

    public PaginationResultDto getPageList(BoardQueryDto boardQueryDto) {
        //고정목록 조회
        List<Notice> results = this.noticeMapper.findAll(Notice.builder()
                .noticeYn(Constants.Y)
                .build());
        // 페이징 건수 조회
        long count = this.noticeMapper.count(boardQueryDto);
        // 일반공지사항 조회
        results.addAll(this.noticeMapper.findPage(boardQueryDto));
        return PaginationResultDto.builder()
                .count(count)
                .page(boardQueryDto.getPage())
                .limit(boardQueryDto.getLimit())
                .result(results.stream().map(v -> {
                    NoticeDto noticeDto = this.convert(v, NoticeDto.class);
                    // 첨부파일유무
                    noticeDto.setAttachment(this.noticeAttachMapper.exists(NoticeAttach.builder().noticeId(noticeDto.getNoticeId()).build()));
                    return noticeDto;
                }).collect(Collectors.toList()))
                .build();
    }
}
