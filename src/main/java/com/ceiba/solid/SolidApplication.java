package com.ceiba.solid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SolidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolidApplication.class, args);
	}

}
