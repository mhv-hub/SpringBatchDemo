package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.writer;

import com.mhv.batchprocessing.entity.Customer;
import com.mhv.batchprocessing.service.kafka.KafkaProducerService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;

@Component
@Qualifier(value = "customerMessageEventWriter")
public class CustomerWriterKafka implements ItemWriter<Customer> {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("${kafka.csv.topic.customer}")
    private String customerDataTopic;

    @Value("${kafka.csv.topic.customer_ctl}")
    private String customerCtlTopic;

    @Override
    public void write(Chunk<? extends Customer> customers) throws Exception {
        Exception exception = null;
        exception = kafkaProducerService.pushCustomerDataToQueue(new ArrayList<>(customers.getItems()), customerDataTopic);
        if(exception == null){
            System.out.println("Customer data published to message queue [ " + LocalTime.now() + " ]");
            exception = kafkaProducerService.pushCustomerCtlToQueue(customerCtlTopic);
            System.out.println(exception == null ? "Customer CTL published to message queue [ " + LocalTime.now() + " ]" : "Issue in pushing customer CTL to message queue : [ " + LocalTime.now() + " ]" + exception.getMessage());
        }
        else {
            System.out.println("Issue in pushing customer data to message queue : [ " + LocalTime.now() + " ]" + exception.getMessage());
        }

    }
}
