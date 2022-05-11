package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.dto.PagingQueryDto;
import com.utour.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends CommonMapper<User> {

    void updateYn(User user);

    List<User> findPage(PagingQueryDto queryDto);

    long count(PagingQueryDto queryDto);
}
