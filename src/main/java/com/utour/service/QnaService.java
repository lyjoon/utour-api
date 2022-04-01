package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.qna.QnaDto;
import com.utour.dto.qna.QnaReplyDto;
import com.utour.entity.Qna;
import com.utour.entity.QnaReply;
import com.utour.exception.InternalException;
import com.utour.mapper.QnaMapper;
import com.utour.mapper.QnaReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService extends CommonService {

    private final QnaMapper qnaMapper;
    private final QnaReplyMapper qnaReplyMapper;

    /**
     * 저장
     */
    public void save(QnaDto qnaDto) {
        Qna qna = this.convert(qnaDto, Qna.class);
        this.qnaMapper.save(qna);
    }

    /**
     * 삭제
     * @param qnaDto
     */
    public void delete(QnaDto qnaDto) {
        Qna qna = this.qnaMapper.findById(Qna.builder().qnaId(qnaDto.getQnaId()).build());
        if(Objects.isNull(qna)) {
            throw new InternalException(this.getMessage("qns.service.delete.error.001"));
        }
        boolean isMatched = qna.getPassword().equals(qnaDto.getPassword());
        if(isMatched) {
            this.qnaReplyMapper.delete(QnaReply.builder().qnaId(qnaDto.getQnaId()).build());
            this.qnaMapper.delete(qna);
        } else {
            throw new InternalException(this.getMessage("qns.service.delete.error.002"));
        }
    }

    /**
     * 삭제
     * @param qnaReplyDto
     */
    public void delete(QnaReplyDto qnaReplyDto) {
        QnaReply qnaReply = this.qnaReplyMapper.findById(QnaReply.builder()
                .qnaReplyId(qnaReplyDto.getQnaReplyId())
                .qnaId(qnaReplyDto.getQnaId())
                .build());

        if(Objects.isNull(qnaReply)) {
            throw new InternalException(this.getMessage("qns.service.delete.error.001"));
        }
        boolean isMatched = qnaReply.getPassword().equals(qnaReplyDto.getPassword());
        if(isMatched) {
            QnaReply qnaReply1 = this.convert(qnaReplyDto, QnaReply.class);
            this.qnaReplyMapper.deleteById(qnaReply1);
        } else {
            throw new InternalException(this.getMessage("qns.service.delete.error.002"));
        }
    }

    /**
     * 비밀번호 확인
     * @param qnaDto
     * @return
     */
    public boolean checkup(QnaDto qnaDto){
        Qna qna = this.qnaMapper.findById(Qna.builder().qnaId(qnaDto.getQnaId()).build());
        if(Objects.isNull(qna)) {
            throw new InternalException(this.getMessage("qns.service.delete.error.001"));
        }
        boolean isMatched = qna.getPassword().equals(qnaDto.getPassword());
        return isMatched;
    }

    /**
     * 비밀번호 확인
     * @param qnaReplyDto
     * @return
     */
    public boolean checkup(QnaReplyDto qnaReplyDto){
        QnaReply qnaReply = this.qnaReplyMapper.findById(QnaReply.builder()
                .qnaReplyId(qnaReplyDto.getQnaReplyId())
                .qnaId(qnaReplyDto.getQnaId())
                .build());

        if(Objects.isNull(qnaReply)) {
            throw new InternalException(this.getMessage("qns.service.delete.error.001"));
        }
        boolean isMatched = qnaReply.getPassword().equals(qnaReplyDto.getPassword());
        return isMatched;
    }

    /**
     * 단일항목조회
     * @param qnaId
     * @return
     */
    public QnaDto findById(Long qnaId) {
        return Optional.ofNullable(this.qnaMapper.findById(Qna.builder().qnaId(qnaId).build()))
                .map(v -> this.convert(v, QnaDto.class))
                .orElse(null);
    }

    /**
     * 페이지 목록조회
     * @param boardQueryDto
     * @return
     */
    public java.util.List<QnaDto> findPage(BoardQueryDto boardQueryDto) {
        return this.qnaMapper.findPage(boardQueryDto).stream()
                .map( v -> this.convert(v, QnaDto.class))
                .peek(qnaDto -> qnaDto.setReplyCnt(Optional.ofNullable(this.qnaReplyMapper.count(QnaReply.builder().qnaId(qnaDto.getQnaId()).build()))
                        .orElse(0)))
                .collect(Collectors.toList());
    }

    /**
     * 목록
     * @param qnaId
     * @return
     */
    public java.util.List<QnaReplyDto> findAll(Long qnaId) {
        return Optional.ofNullable(this.qnaReplyMapper.findAll(QnaReply.builder().qnaId(qnaId).build()))
                .map(list -> list.stream().map(v -> this.convert(v , QnaReplyDto.class) ).collect(Collectors.toList()))
                .orElse(null);
    }

    /**
     * 저장
     * @param qnaReplyDto
     */
    public void save(QnaReplyDto qnaReplyDto) {
        this.qnaReplyMapper.save(this.convert(qnaReplyDto, QnaReply.class));
    }
}
