package com.github.blkcor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.blkcor")
public class TrainMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainMemberApplication.class, args);
    }
}
