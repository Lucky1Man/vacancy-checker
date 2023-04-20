package com.vacancy.checker.server.vacancies.storage;

import com.vacancy.checker.server.vacancies.model.Vacancy;

import java.util.Set;

public interface KnownVacanciesStorage {

    Set<Vacancy> findAll();

    void addAll(Set<Vacancy> vacancies);

}
