package com.mhv.batchprocessing.service.kafka.customer;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class CustomerService {

    @Autowired
    private JobLauncher jobLauncher;

//    @Autowired
//    private ExecutionContext executionContext;

    @Autowired
    @Qualifier(value = "customerValidatorJobBean")
    private Job customerValidatorJob;

    public void triggerCustomerValidationJob(String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLocalDateTime("startTime", LocalDateTime.now());
        jobParametersBuilder.addString("fileName", fileName);
//        if(executionContext.containsKey("fileName")) executionContext.remove("fileName");
//        executionContext.putString("fileName", fileName);
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        JobExecution jobExecution = jobLauncher.run(customerValidatorJob, jobParameters);
        while (jobExecution.isRunning()){}
        System.out.println("Job Status : [ " + jobExecution.getStatus() + " ]");
    }
}
