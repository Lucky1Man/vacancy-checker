package com.vacancy.checker.server.vacancies.storage;

import com.vacancy.checker.server.vacancies.model.Vacancy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryVacanciesStorage implements KnownVacanciesStorage{

    private final Set<Vacancy> knownVacancies = new HashSet<>();

    @Override
    public Set<Vacancy> findAll() {
        return knownVacancies;
    }

    @Override
    public void addAll(Set<Vacancy> vacancies) {
        knownVacancies.addAll(vacancies);
    }

}
