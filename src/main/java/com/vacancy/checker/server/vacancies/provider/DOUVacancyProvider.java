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
public class DOUVacancyProvider extends AbstractVacancyProvider<String>{

    private final VacanciesHtmlPageParser douParser;

    public DOUVacancyProvider(VacanciesLinkProvider vacanciesLinkProvider,
                                 VacanciesLoadingProvider<String> vacanciesLoadingProvider,
                                 @Qualifier("vacanciesStorageImpl")
                                 KnownVacanciesStorage knownVacanciesStorage,
                                 @Qualifier("DOUParser")
                                 VacanciesHtmlPageParser douParser) {
        super(vacanciesLinkProvider, vacanciesLoadingProvider, knownVacanciesStorage, log);
        this.douParser = douParser;
    }

    @Override
    protected Set<Vacancy> extractAllAvailableVacancies(VacancyLinkToResponseEntity<String> vacancyLinkToResponseEntity) {
        return new HashSet<>(douParser.getAllVacancies(vacancyLinkToResponseEntity));
    }

    @Override
    protected VacancySite getVacancySite() {
        return VacancySite.DOU;
    }
}
