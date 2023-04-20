package com.vacancy.checker.server.vacancies.watcher;

import com.vacancy.checker.server.messaging.MessagingProvider;
import com.vacancy.checker.server.vacancies.provider.VacancyProvider;

import java.time.Duration;

public abstract class VacancySiteWatcher implements Runnable {

    private final MessagingProvider messagingProvider;

    private final VacancyProvider vacancyProvider;

    protected VacancySiteWatcher(MessagingProvider messagingProvide,
                                 VacancyProvider vacancyProvider) {
        this.messagingProvider = messagingProvide;
        this.vacancyProvider = vacancyProvider;
    }

    @Override
    public final void run() {
        messagingProvider.messageAllClientsAboutNewVacancies(vacancyProvider.getAllNewVacancies());
    }

    public abstract Duration getCheckPeriod();

}
