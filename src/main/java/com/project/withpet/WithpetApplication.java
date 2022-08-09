package com.project.withpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class WithpetApplication {



	public static void main(String[] args) {

		SpringApplication.run(WithpetApplication.class, args);
	}

}