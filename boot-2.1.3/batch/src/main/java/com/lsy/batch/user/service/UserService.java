package com.lsy.batch.user.service;

import com.lsy.batch.user.mapper.UserMapper;
import com.lsy.batch.user.model.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserMapper userMapper;

    public List<UserVo> selectUserList() {
        return userMapper.selectUserList();
    }

    @Transactional
    public int insertBatchTest(String userId) {
        return userMapper.insertBatchTest(userId);
    }
}
