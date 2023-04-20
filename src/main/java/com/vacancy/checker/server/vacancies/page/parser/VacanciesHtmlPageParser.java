package com.vacancy.checker.server.vacancies.page.parser;

import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.provider.VacancyLinkToResponseEntity;

import java.util.List;

public interface VacanciesHtmlPageParser {

    List<Vacancy> getAllVacancies(VacancyLinkToResponseEntity<String> vacancyLinkToResponseEntity);

}
