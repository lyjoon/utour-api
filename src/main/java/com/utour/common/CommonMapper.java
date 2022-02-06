package com.utour.common;

import java.util.List;

public interface CommonMapper <T>{

    /**
     * 신규저장
     * @param t
     */
    void insert(T t);

    /**
     * 삭제
     * @param t
     * @return
     */
    Long delete(T t);

    /**
     * 수정
     * @param t
     * @return
     */
    Long update(T t);

    /**
     * 데이터 검색
     * @param t
     * @return
     */
    T findById(T t);

    /**
     * 데이터 목록조회
     * @param t
     * @return
     */
    List<T> findAll(T t);

    /**
     * 데이터 유무 검색
     * @param t
     * @return
     */
    Boolean exists(T t);
}
