package com.project.withpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WithpetApplication {

//	static{
//		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
//	}

	public static void main(String[] args) {

		SpringApplication.run(WithpetApplication.class, args);
	}

}