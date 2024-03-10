package org.androsovich.applications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BidsApplication {
	public static void main(String[] args) {
		SpringApplication.run(BidsApplication.class, args);
	}
}
