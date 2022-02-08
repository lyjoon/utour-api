package com.utour.common;

import java.util.List;

public interface CommonMapper <T>{

    /**
     * 삭제
     * @param t
     * @return
     */
    Long delete(T t);

    /**
     * 저장
     * @param t
     * @return
     */
    void save(T t);

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
