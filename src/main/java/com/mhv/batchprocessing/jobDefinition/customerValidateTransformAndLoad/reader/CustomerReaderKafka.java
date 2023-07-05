package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.reader;

import com.mhv.batchprocessing.entity.Customer;
import com.mhv.batchprocessing.util.CustomerJsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
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
import java.util.Properties;

@Component
public class CustomerReaderKafka implements ItemReader<Customer> {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${kafka.customer.data.group.id}")
    private String groupId;

    @Value("${kafka.csv.topic.customer}")
    private String topic;

    @Bean(name = "customerKafkaQueueReaderBean")
    public KafkaItemReader<String, Customer> itemReader(){
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomerJsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaItemReaderBuilder<String, Customer>()
                .partitions(0)
                .partitionOffsets(new HashMap<>())
                .consumerProperties(config)
                .name("kafkaItemReader")
                .saveState(true)
                .topic(topic)
                .build();
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
