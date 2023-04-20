package com.vacancy.checker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VacancyCheckerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacancyCheckerServerApplication.class, args);
    }

}
