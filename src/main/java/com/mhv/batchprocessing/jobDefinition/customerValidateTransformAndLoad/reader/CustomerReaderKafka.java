package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.reader;

import com.mhv.batchprocessing.entity.Customer;
import com.mhv.batchprocessing.util.CustomerJsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Component
public class CustomerReaderKafka {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${kafka.customer.data.group.id}")
    private String groupId;

    @Value("${kafka.csv.topic.customer}")
    private String topic;

    @Bean(name = "customerKafkaQueueReaderBean")
    @StepScope
    public KafkaItemReader<String, Customer> itemReader(){
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomerJsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaItemReaderBuilder<String, Customer>()
                .partitions(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
                .partitionOffsets(new HashMap<>())
                .consumerProperties(config)
                .name("kafkaItemReader")
                .topic(topic)
                .build();
    }
}
