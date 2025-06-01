package com.example.eventmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients; // Import EnableFeignClients

@SpringBootApplication
@EnableDiscoveryClient // Keep this if using Eureka
@EnableFeignClients // Enable Feign clients in this application
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}

