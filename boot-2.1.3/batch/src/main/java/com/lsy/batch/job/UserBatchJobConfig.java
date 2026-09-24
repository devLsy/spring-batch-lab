package com.lsy.batch.job;

import com.lsy.batch.tasklet.UserBatchTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserBatchJobConfig {

    private static final String JOB_NAME = "userBatchJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserBatchTasklet userBatchTasklet;

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("userBatchStep1")
                .tasklet(userBatchTasklet)
                .build();
    }
}
