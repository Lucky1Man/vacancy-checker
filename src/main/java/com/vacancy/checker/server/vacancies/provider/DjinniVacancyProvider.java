package com.vacancy.checker.server.vacancies.provider;

import com.vacancy.checker.server.vacancies.link.provider.VacanciesLinkProvider;
import com.vacancy.checker.server.vacancies.loader.VacanciesLoadingProvider;
import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.model.VacancySite;
import com.vacancy.checker.server.vacancies.page.parser.VacanciesHtmlPageParser;
import com.vacancy.checker.server.vacancies.storage.KnownVacanciesStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class DjinniVacancyProvider extends AbstractVacancyProvider<String> {

    private final VacanciesHtmlPageParser djinniParser;

    public DjinniVacancyProvider(
            VacanciesLinkProvider vacanciesLinkProvider,
            VacanciesLoadingProvider<String> vacanciesLoadingProvider,
            KnownVacanciesStorage knownVacanciesStorage,
            @Qualifier("djinniParser")
            VacanciesHtmlPageParser djinniParser) {
        super(vacanciesLinkProvider, vacanciesLoadingProvider, knownVacanciesStorage, log);
        this.djinniParser = djinniParser;
    }

    @Override
    protected Set<Vacancy> extractAllAvailableVacancies(VacancyLinkToResponseEntity<String> vacancyLinkToResponseEntity) {
        return new HashSet<>(djinniParser.getAllVacancies(vacancyLinkToResponseEntity));
    }

    @Override
    protected VacancySite getVacancySite() {
        return VacancySite.DJINNI;
    }

}
