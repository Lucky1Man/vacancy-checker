package com.vacancy.checker.server.vacancies.loader;

import org.springframework.http.ResponseEntity;

public interface VacanciesLoadingProvider <T> {

    ResponseEntity<T> getContentWithVacancies(String linkToContent);

}
