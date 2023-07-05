package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.step;

import com.mhv.batchprocessing.entity.Customer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class CustomerTransformationLoadStep {

    @Autowired
    @Qualifier(value = "customerKafkaQueueReaderBean")
    private KafkaItemReader<String, Customer> itemReader;

    @Autowired
    @Qualifier(value = "customerTransformationProcessorBean")
    private ItemProcessor<Customer, Customer> itemProcessor;

    @Autowired
    @Qualifier(value = "customerDataWriterBean")
    private ItemWriter<Customer> itemWriter;

    @Bean(name = "customerTransformationLoadStepBean")
    public Step getCustomerTransformationLoadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("customer-transform-and-load-step", jobRepository)
                .<Customer, Customer>chunk(100, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }
}
