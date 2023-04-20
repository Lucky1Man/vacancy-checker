package com.vacancy.checker.server.vacancies.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class Vacancy {

    private final String linkToVacancy;
    private final String vacancyTitle;
    private final ExperienceLevel experienceLevel;
    private final VacancySite vacancySite;

}
