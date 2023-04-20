package com.vacancy.checker.server.messaging;

import com.vacancy.checker.server.vacancies.model.Vacancy;

import java.util.List;

public interface MessagingProvider {

    void messageAllClientsAboutNewVacancies(List<Vacancy> vacancies);

}
