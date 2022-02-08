package com.utour.mapper;

import com.utour.mapper.common.CommonMapper;
import com.utour.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends CommonMapper<Member> {}
