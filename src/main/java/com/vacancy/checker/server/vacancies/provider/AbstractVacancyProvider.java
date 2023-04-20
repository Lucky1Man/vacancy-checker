package com.vacancy.checker.server.vacancies.provider;

import com.vacancy.checker.server.vacancies.model.LinkToVacancies;
import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.model.VacancySite;
import com.vacancy.checker.server.vacancies.storage.KnownVacanciesStorage;
import com.vacancy.checker.server.vacancies.link.provider.VacanciesLinkProvider;
import com.vacancy.checker.server.vacancies.loader.VacanciesLoadingProvider;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractVacancyProvider<T> implements VacancyProvider {

    private final VacanciesLinkProvider vacanciesLinkProvider;

    private final VacanciesLoadingProvider<T> vacanciesLoadingProvider;

    private final KnownVacanciesStorage knownVacanciesStorage;

    private final Logger log;

    protected AbstractVacancyProvider(VacanciesLinkProvider vacanciesLinkProvider,
                                      VacanciesLoadingProvider<T> vacanciesLoadingProvider,
                                      KnownVacanciesStorage knownVacanciesStorage,
                                      Logger log) {
        this.vacanciesLinkProvider = vacanciesLinkProvider;
        this.vacanciesLoadingProvider = vacanciesLoadingProvider;
        this.knownVacanciesStorage = knownVacanciesStorage;
        this.log = log;
    }


    @Override
    public final List<Vacancy> getAllNewVacancies() {
        Set<Vacancy> receivedVacancies = vacanciesLinkProvider.getLinksRelatedWithSite(getVacancySite()).stream()
                .map(linkToLinkWithResponseMapper())
                .filter(successfulResponseFilter())
                .map(this::extractAllAvailableVacancies)
                .reduce(new HashSet<>(), addAllAccumulator());
        receivedVacancies.removeAll(knownVacanciesStorage.findAll());
        knownVacanciesStorage.addAll(receivedVacancies);
        return new LinkedList<>(receivedVacancies);
    }

    private Function<LinkToVacancies, VacancyLinkToResponseEntity<T>> linkToLinkWithResponseMapper() {
        return linkToVacancies -> VacancyLinkToResponseEntity.<T>builder()
                .withLinkToVacancies(linkToVacancies)
                .withGotResponse(vacanciesLoadingProvider.getContentWithVacancies(linkToVacancies.getLink()))
                .build();
    }

    private Predicate<VacancyLinkToResponseEntity<T>> successfulResponseFilter() {
        return vacancyLinkToResponseEntity -> {
            if (vacancyLinkToResponseEntity.getGotResponse().getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                log.warn("Error during request to site {}, {}",
                        vacancyLinkToResponseEntity.getLinkToVacancies().getVacancySite(),
                        vacancyLinkToResponseEntity.getGotResponse());
                return false;
            }
        };
    }

    private static BinaryOperator<Set<Vacancy>> addAllAccumulator() {
        return (vacancies, vacancies2) -> {
            vacancies.addAll(vacancies2);
            return vacancies;
        };
    }

    protected abstract Set<Vacancy> extractAllAvailableVacancies(VacancyLinkToResponseEntity<T> vacancyLinkToResponseEntity);

    protected abstract VacancySite getVacancySite();

}


