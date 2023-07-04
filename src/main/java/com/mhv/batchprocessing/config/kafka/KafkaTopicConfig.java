package com.mhv.batchprocessing.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.csv.topic.customer}")
    private String customerDataTopic;

    @Bean
    public NewTopic newItemTopic(){
        return TopicBuilder.name(customerDataTopic).build();
    }
}
