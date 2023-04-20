package com.vacancy.checker.server.messaging;

import com.vacancy.checker.server.vacancies.model.Vacancy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsoleMessagingProvider implements MessagingProvider {

    @Override
    public void messageAllClientsAboutNewVacancies(List<Vacancy> vacancies) {
        vacancies.forEach(
                vacancy -> log.info(vacancy.toString())
        );
    }

}
