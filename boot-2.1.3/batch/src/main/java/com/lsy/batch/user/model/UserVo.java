package com.lsy.batch.user.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVo {
    private Long userId;
    private String loginId;
    private String password;
    private String userName;
    private String email;
    private String phone;
    private String statusCd;
    private Integer loginFailCnt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
