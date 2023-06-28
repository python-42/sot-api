package com.sotindex.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApiApplication {

	private static ConfigurableApplicationContext ctx;
	public static void main(String[] args) {
		ctx = SpringApplication.run(ApiApplication.class, args);
	}

	public static void exitApplication(int code) {
		SpringApplication.exit(ctx);
		System.exit(code);
	}

}
