package com.secondprojinitiumback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SecondProjInitiumBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondProjInitiumBackApplication.class, args);
    }

}
