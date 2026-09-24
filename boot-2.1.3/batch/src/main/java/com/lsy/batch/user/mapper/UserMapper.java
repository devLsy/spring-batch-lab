package com.lsy.batch.user.mapper;

import com.lsy.batch.user.model.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> selectUserList();
    int insertBatchTest(@Param("userId") String userId);
}
