package com.vacancy.checker.server.vacancies.provider;

import com.vacancy.checker.server.vacancies.model.LinkToVacancies;
import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.model.VacancySite;
import com.vacancy.checker.server.vacancies.storage.KnownVacanciesStorage;
import com.vacancy.checker.server.vacancies.link.provider.VacanciesLinkProvider;
import com.vacancy.checker.server.vacancies.loader.VacanciesLoadingProvider;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;

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
        Set<Vacancy> receivedVacancies = vacanciesLinkProvider.getLinksRelatedWithSite(VacancySite.DJINNI).stream()
                .map(linkToLinkWithResponseMapper())
                .filter(successfulResponseFilter())
                .map(this::extractAllAvailableVacancies)
                .reduce(new HashSet<>(), addAllAccumulator());
        receivedVacancies.removeAll(knownVacanciesStorage.findAll());
        knownVacanciesStorage.addAll(receivedVacancies);
        return new LinkedList<>(receivedVacancies);
    }

    private Function<LinkToVacancies, VacancyLinkToResponseEntity> linkToLinkWithResponseMapper() {
        return linkToVacancies -> VacancyLinkToResponseEntity.builder(this)
                .withLinkToVacancies(linkToVacancies)
                .withGotResponse(vacanciesLoadingProvider.getContentWithVacancies(linkToVacancies.getLink()))
                .build();
    }

    private Predicate<VacancyLinkToResponseEntity> successfulResponseFilter() {
        return vacancyLinkToResponseEntity -> {
            if (vacancyLinkToResponseEntity.gotResponse.getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                log.warn("Error during request to site {}, {}",
                        vacancyLinkToResponseEntity.linkToVacancies.getVacancySite(),
                        vacancyLinkToResponseEntity.gotResponse);
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

    protected abstract Set<Vacancy> extractAllAvailableVacancies(VacancyLinkToResponseEntity vacancyLinkToResponseEntity);

    @Data
    public final class VacancyLinkToResponseEntity {
        private final LinkToVacancies linkToVacancies;
        private final ResponseEntity<T> gotResponse;

        private VacancyLinkToResponseEntity(LinkToVacancies linkToVacancies, ResponseEntity<T> gotResponse) {
            this.linkToVacancies = linkToVacancies;
            this.gotResponse = gotResponse;
        }

        public static <T> VacancyLinkToResponseEntityBuilder<T> builder(AbstractVacancyProvider<T> outerInstance) {
            return new VacancyLinkToResponseEntityBuilder<>(outerInstance);
        }

        @ToString
        public static class VacancyLinkToResponseEntityBuilder<T> {
            private LinkToVacancies linkToVacancies;
            private ResponseEntity<T> gotResponse;

            private final AbstractVacancyProvider<T> outerInstance;

            VacancyLinkToResponseEntityBuilder(AbstractVacancyProvider<T> outerInstance) {
                this.outerInstance = outerInstance;
            }

            public VacancyLinkToResponseEntityBuilder<T> withLinkToVacancies(LinkToVacancies linkToVacancies){
                this.linkToVacancies = linkToVacancies;
                return this;
            }

            public VacancyLinkToResponseEntityBuilder<T> withGotResponse(ResponseEntity<T> gotResponse){
                this.gotResponse = gotResponse;
                return this;
            }

            public AbstractVacancyProvider<T>.VacancyLinkToResponseEntity build() {
                return outerInstance.new VacancyLinkToResponseEntity(linkToVacancies, gotResponse);
            }

        }

    }

}


