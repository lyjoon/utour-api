package com.utour.schedule;

import com.utour.batch.job.DeleteTempFileBatchJobConfig;
import com.utour.common.CommonComponent;
import com.utour.exception.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 임시파일삭제 관련 스케줄러 (하루 1회수행)
 */
@Component
@RequiredArgsConstructor
public class DeleteTempFileScheduler extends CommonComponent {

    private final JobLauncher batchJobLauncher;

    @Qualifier(DeleteTempFileBatchJobConfig.JOB_ID)
    private final Job job;

    /**
     * 매일 자정에 실행(cron = "0 0 0 * * ?")
     */
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void dailyScheduler() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobID", Long.toString(System.currentTimeMillis())).toJobParameters();
            batchJobLauncher.run(job, jobParameters);
        } catch (Throwable throwable) {
            throw new InternalException(throwable);
        }
    }
}
