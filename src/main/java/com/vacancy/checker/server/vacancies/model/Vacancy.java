package com.vacancy.checker.server.vacancies.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class Vacancy {

    private final String linkToVacancy;
    private final String vacancyTitle;
    private final ExperienceLevel experienceLevel;
    private final VacancySite vacancySite;

}
