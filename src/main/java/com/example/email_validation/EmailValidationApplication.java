package com.example.email_validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EmailValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailValidationApplication.class, args);
	}

}
