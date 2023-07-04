package com.mhv.batchprocessing.service.kafka;

import com.mhv.batchprocessing.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    public Exception pushCustomerToQueue(List<Customer> customerList, String topic){
        if(customerList == null || customerList.size() == 0)
            return new Exception("No data provided");
        else if(topic == null || topic.length() == 0) {
            return new Exception("No topic provided");
        } else{
            try {
                customerList.forEach(customer -> kafkaTemplate.send(topic, customer));
                return null;
            }catch (Exception e){
                return e;
            }
        }
    }
}
