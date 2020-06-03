package com.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start the application from main method 
 */
@SpringBootApplication
@EnableAutoConfiguration
public class OhlcApplication {

	public static void main(String[] args) {
		SpringApplication.run(OhlcApplication.class, args);
	}

}
