package com.vacancy.checker.server;

import com.vacancy.checker.server.vacancies.repository.VacancyRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Configuration
public class DatabaseClearanceConfig {

    private final VacancyRepository vacancyRepository;

    private final TaskScheduler taskScheduler;

    public DatabaseClearanceConfig(VacancyRepository vacancyRepository,
                                   TaskScheduler taskScheduler) {
        this.vacancyRepository = vacancyRepository;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void initDatabaseClearance() {
        taskScheduler.scheduleWithFixedDelay(clearOlderThan2Weeks(), Duration.ofDays(1));
    }

    private Runnable clearOlderThan2Weeks() {
        return () -> {
            vacancyRepository.deleteAllOlderThan(LocalDateTime.now().minus(2, ChronoUnit.WEEKS));
            vacancyRepository.flush();
        };
    }

}
