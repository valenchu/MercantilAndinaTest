package com.mercantil.example.mercantiltest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@OpenAPIDefinition
@SpringBootApplication
@EnableScheduling
public class MercantiltestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercantiltestApplication.class, args);

    }

}
