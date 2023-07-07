package com.mhv.batchprocessing.service.customer;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerJobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "customerValidatorJobBean")
    private Job customerValidatorJob;

    @Autowired
    @Qualifier(value = "customerTransformAndLoadJobBean")
    private Job customerTransformationAndLoadJob;

    public void triggerCustomerValidationJob(String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLocalDateTime("startTime", LocalDateTime.now());
        jobParametersBuilder.addString("fileName", fileName);
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        JobExecution jobExecution = jobLauncher.run(customerValidatorJob, jobParameters);
        while (jobExecution.isRunning()){}
        System.out.println("Validation Job Status : [ " + jobExecution.getStatus() + " ]");
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
