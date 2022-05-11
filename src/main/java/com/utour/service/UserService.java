package com.utour.service;

import com.utour.common.CommonService;
import com.utour.dto.PagingQueryDto;
import com.utour.dto.PagingResultDto;
import com.utour.dto.user.UserDto;
import com.utour.entity.User;
import com.utour.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends CommonService {

    private final UserMapper userMapper;

    public PagingResultDto getPageList(PagingQueryDto pagingQueryDto) {

        long count = this.userMapper.count(pagingQueryDto);

        java.util.List<UserDto> results = this.userMapper.findPage(pagingQueryDto)
                .stream()
                .map(v -> this.convert(v, UserDto.class))
                .collect(Collectors.toList());

        return PagingResultDto.builder()
                .page(pagingQueryDto.getPage())
                .limit(pagingQueryDto.getLimit())
                .count(count)
                .result(results)
                .build();
    }

    public UserDto get(String userId) {
        return Optional.ofNullable(this.userMapper.findById(User.builder().userId(userId).build()))
                .map(v -> this.convert(v , UserDto.class))
                .orElse(null);
    }

    public void save(UserDto userDto){
        this.userMapper.save(this.convert(userDto));
    }

    public void delete(String userId) {
        this.userMapper.delete(User.builder().userId(userId).build());
    }

    public boolean exists(String userId) {
        return this.userMapper.exists(User.builder().userId(userId).build());
    }

    public void updateYn(UserDto userDto){
        this.userMapper.updateYn(this.convert(userDto));
    }

    private User convert(UserDto userDto) {
        return User.builder()
                .useYn(userDto.getUseYn())
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .build();
    }

}
