package com.utour.mapper;

import com.utour.common.CommonMapper;
import com.utour.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends CommonMapper<User> {}
