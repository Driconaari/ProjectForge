package com.example.projectforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.projectforge.userRepository",
        "com.example.projectforge.service",
        "com.example.projectforge.controller",
        "com.example.projectforge.security",
        "com.example.projectforge.projectRepository",
        "com.example.projectforge.model",
        "com.example.projectforge.ResourceRepository"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

