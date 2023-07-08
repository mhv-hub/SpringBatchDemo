package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.listener;

import com.mhv.batchprocessing.dto.RejectedRecord;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Qualifier("csvProcessorJobListenerBean")
@JobScope
public class CsvProcessorJobListener implements JobExecutionListener {

    @Autowired
    @Qualifier(value = "validationSkipListenerBean")
    private ValidationSkipListener skipListener;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("rejectedRecordList", new ArrayList<RejectedRecord>());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        StepExecution stepExecution = jobExecution.getStepExecutions().stream().filter(value -> value.getStepName().equals("customer-csv-processor")).findFirst().get();
        jobExecution.getExecutionContext().put("totalRecordCount", stepExecution.getReadCount());
        jobExecution.getExecutionContext().put("processedRecordCount", stepExecution.getWriteCount());
        jobExecution.getExecutionContext().put("rejectedRecordCount", stepExecution.getReadSkipCount() + stepExecution.getProcessSkipCount() + stepExecution.getWriteSkipCount());
        jobExecution.setExitStatus(stepExecution.getExitStatus());
    }
}
