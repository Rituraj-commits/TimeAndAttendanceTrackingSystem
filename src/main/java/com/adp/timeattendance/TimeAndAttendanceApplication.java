package com.adp.timeattendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class })
public class TimeAndAttendanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeAndAttendanceApplication.class, args);
		System.out.println("Hello World Spring Boot");
	}

}
