package com.project.eventregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.project.eventregister")
@EntityScan(basePackages = "com.project.eventregister.models")
public class EventRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventRegisterApplication.class, args);
	}

}
