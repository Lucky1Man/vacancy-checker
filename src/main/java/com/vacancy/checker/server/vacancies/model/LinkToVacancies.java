package com.vacancy.checker.server.vacancies.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class LinkToVacancies {

    private final String link;
    private final VacancySite vacancySite;
    private final ExperienceLevel experienceLevel;

}
