package com.vacancy.checker.server.vacancies.page.parser;

import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.provider.AbstractVacancyProvider;

import java.util.List;

public interface VacanciesHtmlPageParser {

    List<Vacancy> getAllVacancies(AbstractVacancyProvider<String>.VacancyLinkToResponseEntity vacancyLinkToResponseEntity);

}
