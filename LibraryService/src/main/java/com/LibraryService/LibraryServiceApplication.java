package com.LibraryService;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import com.LibraryService.BusinessLogic.LibraryManagementService;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class LibraryServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(LibraryServiceApplication.class, args);
		LibraryManagementService mm=context.getBean(LibraryManagementService.class);
		List<Integer> ll=mm.getCurrentUserBooks("vivsharma");
		for(int i=0; i<ll.size(); i++)
		System.out.println(ll.get(i));
	}

}
