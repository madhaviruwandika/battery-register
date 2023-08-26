package com.powerLedger.registry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@Slf4j
@SpringBootApplication
public class BatteryRegistryServiceApplication {

	public static void main(String[] args) {
		log.info("APP STARTED");
		SpringApplication.run(BatteryRegistryServiceApplication.class, args);
	}

}
