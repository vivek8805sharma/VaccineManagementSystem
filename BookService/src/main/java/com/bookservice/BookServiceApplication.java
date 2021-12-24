package com.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaClient
public class BookServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BookServiceApplication.class, args);
	}

}
