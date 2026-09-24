package com.lsy.batch.tasklet;

import com.lsy.batch.user.model.UserVo;
import com.lsy.batch.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserBatchTasklet implements Tasklet {

    private final UserService userService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        log.info(" ================= User Batch Step1 시작 =================");

        List<UserVo> userList = userService.selectUserList();
        log.info("조회된 사용자 수: {}", userList.size());
        log.info("================= User Batch Step1 종료 =================");

        return RepeatStatus.FINISHED;
    }
}
