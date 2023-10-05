package com.powerledger.batterysyncservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class BatterySyncServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatterySyncServiceApplication.class, args);
    }
}
