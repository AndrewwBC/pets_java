package com.example.pets4ever;

import com.example.pets4ever.services.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Pets4everApplication {

	public static void main(String[] args) {

		new EmailService().sendEmail("andrewborgescampos@gmail.com", "Java Test", "<h1>Texto<h1/>");
		SpringApplication.run(Pets4everApplication.class, args);

	}

}
