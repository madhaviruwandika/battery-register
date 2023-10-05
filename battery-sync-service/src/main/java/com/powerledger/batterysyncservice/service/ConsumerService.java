package com.powerledger.batterysyncservice.service;

import com.powerledger.batterysyncservice.model.Battery;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ConsumerService {
    @Value("${topic.name.consumer")
    private String topicName;
    @Value("${spring.kafka.consumer.group-id}")
    public String groupId;
    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "batteryListener")
    public void consume(Battery battery){
        System.out.println("===Message Received===");
        System.out.println("Batter Name : "+battery.getName()+" | capacity: "+battery.getCapacity());
    }
}
