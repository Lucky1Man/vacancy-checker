package com.vacancy.checker.server;

import com.vacancy.checker.server.vacancies.watcher.VacancySiteWatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.util.List;

@Configuration
public class SiteWatchersConfig {

    private final List<VacancySiteWatcher> siteWatchers;

    private final TaskScheduler taskScheduler;

    public SiteWatchersConfig(
            List<VacancySiteWatcher> siteWatchers,
            TaskScheduler taskScheduler) {
        this.siteWatchers = siteWatchers;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void scheduleSiteWatchers() {
        siteWatchers.forEach(
                siteWatcher -> taskScheduler.scheduleWithFixedDelay(siteWatcher, siteWatcher.getCheckPeriod())
        );
    }

}

