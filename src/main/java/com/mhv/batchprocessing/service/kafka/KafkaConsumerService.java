package com.mhv.batchprocessing.service.kafka;

import com.mhv.batchprocessing.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class KafkaConsumerService {

    @Autowired
    private CustomerService customerService;

    @KafkaListener(id = "customerCtlListener", topics = "${kafka.csv.topic.customer_ctl}", containerFactory = "kafkaCustomerCtlListenerContainerFactoryBean", groupId = ("${kafka.customer.ctl.group.id}"))
    public void consume(String data) {
        System.out.println("********* " + data);
        try {
            customerService.triggerCustomerTransformationAndLoadJob();
        }catch (Exception e){
            System.out.println("Exception in starting transformation-load job : " + e.getMessage());
        }
    }
}
