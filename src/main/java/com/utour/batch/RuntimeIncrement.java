package com.utour.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class RuntimeIncrement implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters jobParameters = parameters == null ? new JobParameters() : parameters;
        return (new JobParametersBuilder(jobParameters)).addLong("run.id", System.currentTimeMillis()).toJobParameters();
    }
}
