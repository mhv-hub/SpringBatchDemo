package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.listener;

import com.mhv.batchprocessing.service.kafka.KafkaProducerService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "validationStepListenerBean")
@StepScope
public class ValidationStepListener implements StepExecutionListener {

    @Value("${kafka.csv.topic.customer_ctl}")
    private String customerCtlTopic;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        int retryCount = 3;
        Exception exception;
        do {
            exception = kafkaProducerService.pushCustomerCtlToQueue(customerCtlTopic);
            retryCount--;
        } while (retryCount > 0 && exception != null);
        if(exception != null){
            System.out.println("Publishing customer ctl event to kafka failed [ Error : " + exception.getMessage());
            return new ExitStatus(ExitStatus.FAILED.getExitCode(), "Customer data processing completed but kafka event publish failed [ Error : " + exception.getMessage() + " ]");
        }else{
            System.out.println("Published the customer ctl event to kafka");
            return new ExitStatus(ExitStatus.COMPLETED.getExitCode(), "Customer data processed and event published to kafka.");
        }
    }
}
