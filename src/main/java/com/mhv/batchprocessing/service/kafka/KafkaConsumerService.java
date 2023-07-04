package com.mhv.batchprocessing.service.kafka;

import com.mhv.batchprocessing.entity.Customer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${kafka.csv.topic.customer}", containerFactory = "kafkaListenerContainerFactory", groupId = ("${kafka.csv.group.id}"))
    public void consume(Customer customer){
        System.out.println("Consumed : " + customer);
    }
}
