package com.lsy.batch.user.service;

import com.lsy.batch.user.mapper.UserMapper;
import com.lsy.batch.user.model.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public List<UserVo> selectUserList() {
        return userMapper.selectUserList();
    }
}
