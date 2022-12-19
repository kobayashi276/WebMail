package com.javafinal.WebMail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication()
public class WebMailApplication {
	//file main
	public static void main(String[] args) {
		SpringApplication.run(WebMailApplication.class, args);
	}

}
