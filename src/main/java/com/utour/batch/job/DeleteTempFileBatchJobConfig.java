package com.utour.batch.job;

import com.utour.common.CommonBatchJob;
import com.utour.exception.InternalException;
import com.utour.util.ErrorUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * temp 경로 오래된 업로드 파일삭제 batch job
 */
@Configuration
public class DeleteTempFileBatchJobConfig extends CommonBatchJob {

    public final static String JOB_ID = "DeleteTempFileBatchJob";

    @Value(value = "${app.file-upload-storage.temp:}")
    private Path tempPath;

    @Value(value = "${app.delete-temp-file.limit:100}")
    private int limit;

    @Value(value = "${app.delete-temp-file.storage-period:7}")
    private int storagePeriod;

    @Bean(JOB_ID)
    public Job stepNextJob(@Qualifier(JOB_ID + STEP1) Step step){
        return this.getJobBuilderFactory().get(JOB_ID)
                //.incrementer(new com.utour.batch.RuntimeIncrement())
                .start(step)
                .build();
    }

    @Bean(JOB_ID + STEP1)
    public Step step1(){
        return this.getStepBuilderFactory().get(JOB_ID + STEP1)
                .tasklet((contribution, chunkContext) -> {

                    AtomicInteger increments = new AtomicInteger(0);
                    LocalDate now = LocalDate.now();
                    Predicate<Path> predicate = path -> increments.get() < this.limit;

                    this.directoryHandler(tempPath, predicate,  path -> {
                        try {
                            FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
                            LocalDate createAt = Instant.ofEpochMilli(creationTime.toMillis()).atZone(ZoneId.systemDefault()).toLocalDate();
                            int diff = Period.between(createAt, now).getDays();
                            if(diff > this.storagePeriod) {
                                try {
                                    Files.delete(path);
                                    log.info("remove success temp-file => [{}]", path.toFile().getPath());
                                    increments.incrementAndGet();
                                } catch (IOException ioException) {
                                    log.warn("remove fail temp-file => [{}] - {}", path.toFile().getPath(), ioException.getMessage());
                                }
                            }
                        } catch (IOException e) {
                            log.warn("{}", ErrorUtils.throwableInfo(e));
                        }
                    });
                    if(increments.get() < 1) {
                        log.info("could not found delete target file => [CNT:{}]", increments.get());
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private void directoryHandler(Path dirPath, Predicate<Path> predicate, Consumer<Path> fileHandler) {
        try {
            if(Files.isDirectory(dirPath)) {
                Files.list(dirPath).filter(predicate).forEach(path -> {
                    if(Files.isRegularFile(path)) {
                        fileHandler.accept(path);
                    } else if (Files.isDirectory(path)) {
                        directoryHandler(path, predicate, fileHandler);
                    }
                });
            } else if (Files.isRegularFile(dirPath)){
                fileHandler.accept(dirPath);
            }
        } catch (IOException ioException) {
            log.error("{}", ErrorUtils.throwableInfo(ioException));
            throw new InternalException(ioException);
        }
    }
}
