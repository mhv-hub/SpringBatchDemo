package com.mhv.batchprocessing.service.customer;

import com.mhv.batchprocessing.dto.JobStatusResponse;
import com.mhv.batchprocessing.dto.RejectedRecord;
import com.mhv.batchprocessing.util.Helper;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@SuppressWarnings({"ConstantConditions", "unchecked", "StatementWithEmptyBody"})
public class CustomerJobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "customerValidatorJobBean")
    private Job customerValidatorJob;

    @Autowired
    @Qualifier(value = "customerTransformAndLoadJobBean")
    private Job customerTransformationAndLoadJob;

    public JobStatusResponse triggerCustomerValidationJob(String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLocalDateTime("startTime", LocalDateTime.now());
        jobParametersBuilder.addString("fileName", fileName);
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        JobExecution jobExecution = jobLauncher.run(customerValidatorJob, jobParameters);
        while (jobExecution.isRunning()){}
        ExitStatus exitStatus = jobExecution.getExitStatus();
        LocalDateTime startTime = jobExecution.getStartTime();
        LocalDateTime endTime = jobExecution.getEndTime();
        String duration = Helper.getDateTimeDifference(startTime, endTime);
        long totalRecordCount = (long)jobExecution.getExecutionContext().get("totalRecordCount");
        long processedRecordCount = (long)jobExecution.getExecutionContext().get("processedRecordCount");
        long rejectedRecordCount = (long)jobExecution.getExecutionContext().get("rejectedRecordCount");
        List<RejectedRecord> rejectedRecords = (List<RejectedRecord>) jobExecution.getExecutionContext().get("rejectedRecordList");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
        System.out.println("Validation job completed <<<<<<<<<<<<<<<<<<<<<<<");
        return new JobStatusResponse(startTime.format(dateTimeFormatter), endTime.format(dateTimeFormatter), duration, exitStatus.getExitCode(), exitStatus.getExitDescription(), totalRecordCount, processedRecordCount, rejectedRecordCount, rejectedRecords, exitStatus.getExitCode().equals(ExitStatus.FAILED.getExitCode()) ? 500 : 200);
    }

    public void triggerCustomerTransformationAndLoadJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLocalDateTime("startTime", LocalDateTime.now());
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        JobExecution jobExecution = jobLauncher.run(customerTransformationAndLoadJob, jobParameters);
        while (jobExecution.isRunning()){}
        System.out.println("Transformation and Load Job Status : [ " + jobExecution.getStatus() + " ]");
    }
}
