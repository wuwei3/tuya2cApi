package com.orangelabs.tuya2capi.tuya2cApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class Tuya2cApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tuya2cApiApplication.class, args);
	}

}
