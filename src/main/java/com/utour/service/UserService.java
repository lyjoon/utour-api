package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.PagingQueryDto;
import com.utour.dto.PagingResultDto;
import com.utour.entity.User;
import com.utour.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends CommonService {

    private final UserMapper userMapper;

    public PagingResultDto getPageList(PagingQueryDto pagingQueryDto) {
        long count = this.userMapper.count(pagingQueryDto);
        return PagingResultDto.builder()
                .page(pagingQueryDto.getPage())
                .limit(pagingQueryDto.getLimit())
                .count(count)
                .result(this.userMapper.findPage(pagingQueryDto))
                .build();
    }

    public void save(User user){
        this.userMapper.save(user);
    }

    public void delete(String userId) {
        this.userMapper.delete(User.builder().userId(userId).build());
    }

    public boolean exists(String userId) {
        return this.userMapper.exists(User.builder().userId(userId).build());
    }

    public void updateYn(String userId, Character useYn){
        this.userMapper.updateYn(User.builder().userId(userId).useYn(useYn).build());
    }
}
