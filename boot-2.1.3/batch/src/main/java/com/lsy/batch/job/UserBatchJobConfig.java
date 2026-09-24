package com.lsy.batch.job;

import com.lsy.batch.tasklet.UserBatchTasklet;
import com.lsy.batch.user.model.UserVo;
import com.lsy.batch.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserBatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserBatchTasklet userBatchTasklet;
    private final SqlSessionFactory sqlSessionFactory;
    private final UserService userService;

    private static final String JOB_NAME = "userBatchJob";
    private static final int CHUNK_SIZE = 3;

    private int writeCount = 0;
    private int chunkCount = 0;

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("userBatchStep1")
                .tasklet(userBatchTasklet)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("userBatchStep2")
                .<UserVo, UserVo>chunk(CHUNK_SIZE)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public MyBatisCursorItemReader<UserVo> reader() {
        return new MyBatisCursorItemReaderBuilder<UserVo>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.lsy.batch.user.mapper.UserMapper.selectUserList")
                .build();
    }

    @Bean
    public ItemWriter<UserVo> writer() {
        return items -> {

            chunkCount++;

            log.info("========== [Chunk {} Writer 시작] ==========", chunkCount);

            for (UserVo user : items) {
                writeCount++;

                log.info("[Chunk {}] [Writer] {}번째 처리 : {}", chunkCount, writeCount, user.getUserId());

                if (writeCount == 6) {
                    try {
                        log.info("========== [Chunk {}] 6번째에서 강제 예외 ==========", chunkCount);
                        throw new RuntimeException("트랜잭션 롤백 테스트");
                    } catch (RuntimeException e) {
                        log.info("========== [Chunk {}] 예외를 Writer에서 잡음 ==========", chunkCount);
                    }
                }
                userService.insertBatchTest(user.getUserId());
            }

            log.info("========== [Chunk {} Writer 종료 / 아직 COMMIT 전] ==========", chunkCount);
        };
    }
}
