package com.example.onetoone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class OneToOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneToOneApplication.class, args);
	}

}
