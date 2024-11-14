package com.example.pets4ever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class Pets4everApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pets4everApplication.class, args);
	}
}
