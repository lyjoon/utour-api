package com.utour.service;

import com.utour.common.CommonService;
import com.utour.common.Constants;
import com.utour.dto.PaginationResultDto;
import com.utour.dto.board.BoardQueryDto;
import com.utour.dto.qna.QnaDto;
import com.utour.dto.qna.QnaReplyDto;
import com.utour.dto.qna.QnaViewDto;
import com.utour.entity.Qna;
import com.utour.entity.QnaReply;
import com.utour.exception.InternalException;
import com.utour.exception.PasswordIncorrectException;
import com.utour.mapper.QnaMapper;
import com.utour.mapper.QnaReplyMapper;
import com.utour.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService extends CommonService {

    private final QnaMapper qnaMapper;
    private final QnaReplyMapper qnaReplyMapper;
    private final LoginService loginService;

    /**
     * 저장
     */
    public void save(QnaDto qnaDto) {
        //Qna qna = this.convert(qnaDto, Qna.class);
        Qna qna = Qna.builder()
                .qnaId(qnaDto.getQnaId())
                .title(qnaDto.getTitle())
                .content(qnaDto.getContent())
                .privateYn(qnaDto.getPrivateYn())
                .pv(qnaDto.getPv())
                .writer(qnaDto.getWriter())
                .password(qnaDto.getPassword())
                .build();
        this.qnaMapper.save(qna);
    }

    /**
     * 삭제
     * @param qnaId 질문과답변 순번
     * @param password 질문과답변 비밀번호
     */
    @Transactional
    public void delete(Long qnaId, String password, String authorization) {
        Qna qna = Qna.builder().qnaId(qnaId).password(password).build();
        if(this.qnaMapper.exists(qna)) {
            boolean isExpired = this.loginService.isExpired(authorization);
            if(isExpired) {
                this.qnaReplyMapper.delete(QnaReply.builder().qnaId(qnaId).build());
                this.qnaMapper.delete(qna);
            } else {
                if(this.qnaMapper.isAccess(qnaId, password)) {
                    this.qnaReplyMapper.delete(QnaReply.builder().qnaId(qnaId).build());
                    this.qnaMapper.delete(qna);
                } else {
                    throw new PasswordIncorrectException(this.getMessage("error.service.qna.delete.invalid-password"));
                }
            }
        } else {
            throw new InternalException(this.getMessage("error.service.qna.not-exists"));
        }
    }

    /**
     * 삭제
     * @param qnaReplyDto dto
     */
    public void delete(QnaReplyDto qnaReplyDto, String authorization) {

        QnaReply qnaReply = QnaReply.builder()
                .qnaReplyId(qnaReplyDto.getQnaReplyId())
                .qnaId(qnaReplyDto.getQnaId())
                .password(qnaReplyDto.getPassword())
                .build();

        boolean exists = this.qnaReplyMapper.exists(qnaReply);
        if(exists) {
            boolean isExpired = this.loginService.isExpired(authorization);
            if(isExpired) {
                this.qnaReplyMapper.delete(qnaReply);
            } else {
                if(this.qnaReplyMapper.isAccess(qnaReply)) {
                    this.qnaReplyMapper.delete(qnaReply);
                } else throw new PasswordIncorrectException(this.getMessage("error.service.qna.delete.invalid-password"));
            }
        } else {
            throw new InternalException(this.getMessage("error.service.qna.not-exists"));
        }
    }

    /**
     * 비밀번호 확인
     * @param qnaId 게시글 순번
     * @param password 비밀번호
     * @return
     */
    public boolean isAccess(Long qnaId, String password){
        return this.qnaMapper.isAccess(qnaId, password);
    }

    /**
     * 비밀번호 확인
     * @param qnaId 게시글 순번
     * @param qnaReplyId 답글 순번
     * @param password 비밀번호
     * @return
     */
    public boolean isAccess(Long qnaId, Long qnaReplyId, String password){
        return this.qnaReplyMapper.isAccess(QnaReply.builder()
                .qnaId(qnaId)
                .qnaReplyId(qnaReplyId)
                .password(password)
                .build());
    }

    /**
     * 단일항목조회
     * @param qnaId
     * @return
     */
    public QnaViewDto get(Long qnaId, String password, String authorization) {
        Qna qna = this.qnaMapper.findById(Qna.builder().qnaId(qnaId).build());
        QnaViewDto qnaViewDto = QnaViewDto.builder()
                .access(false)
                .exists(Objects.nonNull(qna))
                .build();

        if(qnaViewDto.isExists()) {
            boolean isExpired = loginService.isExpired(authorization);
            if(isExpired) {
                qnaViewDto.setAccess(true);
                QnaDto qnaDto = this.convert(qna, QnaDto.class);
                qnaViewDto.setQnaDto(qnaDto);
            } else {
                if(Constants.Y.equals(qna.getPrivateYn())) {
                    if(StringUtils.defaultString(qna.getPassword(), Long.toString(System.currentTimeMillis())).equals(password)) {
                        qnaViewDto.setAccess(true);
                        QnaDto qnaDto = this.convert(qna, QnaDto.class);
                        qnaViewDto.setQnaDto(qnaDto);
                        this.qnaMapper.updateIncrementPv(qna);
                        qnaDto.setPv(1 + qnaDto.getPv());
                    } else {
                        qnaViewDto.setAccess(false);
                    }
                } else {
                    qnaViewDto.setAccess(true);
                    QnaDto qnaDto = this.convert(qna, QnaDto.class);
                    qnaViewDto.setQnaDto(qnaDto);
                    this.qnaMapper.updateIncrementPv(qna);
                    qnaDto.setPv(qnaViewDto.getQnaDto().getPv() + 1);
                }
            }
        }
        Optional.ofNullable(qnaViewDto.getQnaDto()).ifPresent(dto -> dto.setPassword(null));
        return qnaViewDto;
    }

    /**
     * 페이지 목록조회
     * @param boardQueryDto dto
     * @return
     */
    public PaginationResultDto getQnaList(BoardQueryDto boardQueryDto) {

        long count = this.qnaMapper.count(boardQueryDto);
        java.util.List<Qna> list = this.qnaMapper.findPage(boardQueryDto);
        java.util.List<QnaDto> results = list.stream()
                .map( v -> this.convert(v, QnaDto.class))
                .peek(qnaDto -> qnaDto.setReplyCnt(Optional.ofNullable(this.qnaReplyMapper.count(QnaReply.builder().qnaId(qnaDto.getQnaId()).build()))
                        .orElse(0)))
                .collect(Collectors.toList());

        return PaginationResultDto.builder()
                .page(boardQueryDto.getPage())
                .limit(boardQueryDto.getLimit())
                .result(results)
                .count(count)
                .build();
    }

    /**
     * 목록
     * @param qnaId 게시글 순번
     * @return
     */
    public PaginationResultDto getReplies(Long qnaId, Integer page) {
        QnaReply qnaReply = QnaReply.builder().qnaId(qnaId).build();
        long count = this.qnaReplyMapper.count(qnaReply);

        java.util.List<QnaReplyDto> replies = Optional.ofNullable(this.qnaReplyMapper.findAll(qnaReply))
                .map(list -> list.stream().map(v -> this.convert(v , QnaReplyDto.class) ).collect(Collectors.toList()))
                .orElse(null);

        return PaginationResultDto.builder()
                .page(page)
                .limit(Constants.DEFAULT_PAGING_COUNT)
                .result(replies)
                .count(count)
                .build();
    }

    /**
     * 저장
     * @param qnaReplyDto
     */
    public void save(QnaReplyDto qnaReplyDto) {
        QnaReply qnaReply = QnaReply.builder()
                .qnaId(qnaReplyDto.getQnaId())
                .adminYn(qnaReplyDto.getAdminYn())
                .content(qnaReplyDto.getContent())
                .writer(qnaReplyDto.getWriter())
                .password(qnaReplyDto.getPassword())
                .qnaReplyId(qnaReplyDto.getQnaReplyId())
                .privateYn(qnaReplyDto.getPrivateYn())
                .build();
        this.qnaReplyMapper.save(qnaReply);
    }
}
