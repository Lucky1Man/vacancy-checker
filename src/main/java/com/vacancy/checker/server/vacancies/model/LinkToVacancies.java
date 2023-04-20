package com.vacancy.checker.server.vacancies.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class LinkToVacancies {

    private final String link;
    private final VacancySite vacancySite;
    private final ExperienceLevel experienceLevel;

}
