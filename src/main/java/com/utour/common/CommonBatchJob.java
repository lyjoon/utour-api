package com.utour.common;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

public class CommonBatchJob extends CommonComponent {

    protected final static String STEP1 = "$Step1";
    protected final static String STEP2 = "$Step2";
    protected final static String STEP3 = "$Step3";

    protected JobBuilderFactory getJobBuilderFactory(){
        return this.getBean(JobBuilderFactory.class);
    }

    protected StepBuilderFactory getStepBuilderFactory(){
        return this.getBean(StepBuilderFactory.class);
    }
}
