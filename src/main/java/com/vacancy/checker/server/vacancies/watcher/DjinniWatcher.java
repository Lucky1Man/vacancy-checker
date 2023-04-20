package com.vacancy.checker.server.vacancies.watcher;

import com.vacancy.checker.server.messaging.MessagingProvider;
import com.vacancy.checker.server.vacancies.provider.VacancyProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DjinniWatcher extends VacancySiteWatcher {

    private static final Duration checkPeriod = Duration.ofSeconds(5);

    public DjinniWatcher(
            MessagingProvider messagingProvide,
            @Qualifier("djinniVacancyProvider")
            VacancyProvider vacancyProvider) {
        super(messagingProvide, vacancyProvider);
    }

    @Override
    public Duration getCheckPeriod() {
        return checkPeriod;
    }

}
