package com.example.vicarius2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Vicarius2Application {
    public static void main(String[] args) {
        SpringApplication.run(Vicarius2Application.class, args);
    }
}
