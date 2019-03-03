package com.serviceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceRegistryApplication {

	public static void main(String[] args) {
		//Jenkins test build
		SpringApplication.run(ServiceRegistryApplication.class, args);
	}

}
