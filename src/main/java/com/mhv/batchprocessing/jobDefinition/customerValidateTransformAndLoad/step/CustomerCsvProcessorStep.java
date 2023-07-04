package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.step;

import com.mhv.batchprocessing.entity.Customer;
import com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.listener.CsvProcessStepListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class CustomerCsvProcessorStep {

    @Autowired
    @Qualifier(value = "customerCsvFileReader")
    private ItemReader<Customer> itemReader;

    @Autowired
    @Qualifier(value = "customerMessageEventWriter")
    private ItemWriter<Customer> itemWriter;

    @Autowired
    @Qualifier(value = "customerCsvDataProcessor")
    private ItemProcessor<Customer, Customer> itemProcessor;

    @Bean(name = "customerCsvProcessorStepBean")
    public Step getCustomerCsvProcessorStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("customer-csv-processor", jobRepository)
                .<Customer, Customer>chunk(100, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
//                .listener(getCsvProcessStepListener())
                .build();
    }

    @Bean
    public CsvProcessStepListener getCsvProcessStepListener(){
        return new CsvProcessStepListener();
    }
}
