package com.example.pro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProApplication {
    private static final Logger log = LoggerFactory.getLogger(ProApplication.class);

    public static void main(String[] args) {
        log.info("Starting the app...");
        SpringApplication.run(ProApplication.class, args);
        log.info("App started successfully.");
    }

}