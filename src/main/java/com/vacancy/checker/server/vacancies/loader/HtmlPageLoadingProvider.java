package com.vacancy.checker.server.vacancies.loader;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class HtmlPageLoadingProvider implements VacanciesLoadingProvider<String> {

    @Override
    public ResponseEntity<String> getContentWithVacancies(String linkToContent) {
        return new RestTemplate().getForEntity(linkToContent, String.class);
    }

}
