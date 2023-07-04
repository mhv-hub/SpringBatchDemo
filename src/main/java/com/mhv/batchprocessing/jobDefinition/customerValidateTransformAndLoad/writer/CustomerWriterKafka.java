package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.writer;

import com.mhv.batchprocessing.entity.Customer;
import com.mhv.batchprocessing.service.kafka.KafkaProducerService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Qualifier(value = "customerMessageEventWriter")
public class CustomerWriterKafka implements ItemWriter<Customer> {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("${kafka.csv.topic.customer}")
    private String topic;

    @Override
    public void write(Chunk<? extends Customer> customers) throws Exception {
        Exception exception = kafkaProducerService.pushCustomerToQueue(new ArrayList<>(customers.getItems()), topic);
        System.out.println(exception == null ? "Customer data published to message queue" : "Issue in pushing to message queue : " + exception.getMessage());
    }
}
