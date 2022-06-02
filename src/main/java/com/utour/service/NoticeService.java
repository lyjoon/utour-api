package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.PagingResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.notice.NoticeAttachDto;
import com.utour.dto.notice.NoticeDto;
import com.utour.dto.notice.NoticeStoreDto;
import com.utour.dto.notice.NoticeViewDto;
import com.utour.entity.Notice;
import com.utour.entity.NoticeAttach;
import com.utour.exception.InternalException;
import com.utour.mapper.NoticeAttachMapper;
import com.utour.mapper.NoticeMapper;
import com.utour.util.ErrorUtils;
import com.utour.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService extends CommonService {

    private final NoticeMapper noticeMapper;
    private final NoticeAttachMapper noticeAttachMapper;

    @Value(value = "${app.file-upload-storage.notice:}")
    private Path fileStoragePath;

    @Transactional
    public NoticeViewDto get(Long noticeId) {
        Notice notice = Notice.builder().noticeId(noticeId).build();
        boolean exists = noticeMapper.exists(notice);
        if(exists) {
            this.noticeMapper.updateIncrementPv(notice);
            Notice resultEntity = noticeMapper.findById(notice);
            NoticeViewDto noticeViewDto = this.convert(resultEntity, NoticeViewDto.class);
            List<NoticeAttachDto> noticeAttachments = this.noticeAttachMapper.findAll(NoticeAttach.builder().noticeId(noticeId).build()).stream().map(v -> this.convert(v, NoticeAttachDto.class)).collect(Collectors.toList());
            noticeViewDto.setAttachment(!noticeAttachments.isEmpty());
            noticeViewDto.setAttachments(noticeAttachments);
            return noticeViewDto;
        }
        return null;
    }

    public void save(NoticeStoreDto noticeStoreDto, MultipartFile[] attachments) {

        NoticeDto noticeDto = noticeStoreDto.getCommand();
        Notice notice = Notice.builder()
                .noticeId(noticeDto.getNoticeId())
                .content(noticeDto.getContent())
                .noticeYn(noticeDto.getNoticeYn())
                .pv(noticeDto.getPv())
                .title(noticeDto.getTitle())
                .writer(noticeDto.getWriter())
                .build();
        this.noticeMapper.save(notice);

        // 삭제목록 정리
        Optional.ofNullable(noticeStoreDto.getDeleteAttachId()).ifPresent(list -> {
            for (long l : list) {
                NoticeAttach noticeAttach = NoticeAttach.builder().attachId(l).build();
                NoticeAttach noticeAttach1 = this.noticeAttachMapper.findById(noticeAttach);
                if(noticeAttach1 != null) {
                    Path path = Paths.get(noticeAttach1.getPath());
                    try {
                        FileUtils.delete(path);
                    } catch (Throwable e) {
                        log.error("{}", ErrorUtils.throwableInfo(e));
                    } finally {
                        this.noticeAttachMapper.delete(noticeAttach);
                    }
                }
            }
        });

        // cmdType 에 따른 기존파일삭제
        // 신규파일저장
        if(attachments != null && attachments.length > 0) {
            for (MultipartFile multipartFile : attachments) {
                try {
                    Path toPath = FileUtils.uploadFile(fileStoragePath, multipartFile);
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

        NoticeAttach noticeAttach = NoticeAttach.builder()
                .noticeId(noticeId)
                .build();

        List<NoticeAttach> noticeAttachments = this.noticeAttachMapper.findAll(noticeAttach);
        if(!noticeAttachments.isEmpty()) {
            // 파일삭제
            for (NoticeAttach noticeAttachment : noticeAttachments) {
                FileUtils.delete(Paths.get(noticeAttachment.getPath()));
                this.noticeAttachMapper.delete(noticeAttachment);
            }

        }

        this.noticeMapper.delete(Notice.builder()
                .noticeId(noticeId)
                .build());
    }

    public PagingResultDto getPageList(BoardQueryDto boardQueryDto) {
        //고정목록 조회
        List<Notice> results = this.noticeMapper.findAll(Notice.builder()
                .noticeYn(Constants.Y)
                .build());
        int noticeYnCnt = results.size();
        // 페이징 건수 조회
        long count = this.noticeMapper.count(boardQueryDto);
        // 일반공지사항 조회
        results.addAll(this.noticeMapper.findPage(boardQueryDto));
        return PagingResultDto.builder()
                .count(count < 1 && noticeYnCnt > 0 ? 1 : count)
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

    public NoticeAttachDto get(NoticeAttachDto noticeAttachDto) {
        return Optional.ofNullable(this.noticeAttachMapper.findById(NoticeAttach.builder().noticeId(noticeAttachDto.getNoticeId()).attachId(noticeAttachDto.getAttachId()).build()))
                .map(v -> this.convert(v, NoticeAttachDto.class)).orElse(null);
    }
}
