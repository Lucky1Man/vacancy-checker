package com.vacancy.checker.server.vacancies.provider;

import com.vacancy.checker.server.vacancies.model.Vacancy;

import java.util.List;

public interface VacancyProvider {

    List<Vacancy> getAllNewVacancies();

}
