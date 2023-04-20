package com.vacancy.checker.server.vacancies.page.parser;

import com.vacancy.checker.server.vacancies.model.LinkToVacancies;
import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.provider.VacancyLinkToResponseEntity;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class DOUParser implements VacanciesHtmlPageParser{

    @Override
    public List<Vacancy> getAllVacancies(VacancyLinkToResponseEntity<String> vacancyLinkToResponseEntity) {
        List<Vacancy> parseResult = new LinkedList<>();
        Elements foundVacancies = Jsoup.parse(Objects.requireNonNull(vacancyLinkToResponseEntity.getGotResponse().getBody()))
                .select("li.l-vacancy a.vt");
        for (var element : foundVacancies) {
            LinkToVacancies linkToVacancies = vacancyLinkToResponseEntity.getLinkToVacancies();
            parseResult.add(Vacancy.builder()
                    .withVacancySite(linkToVacancies.getVacancySite())
                    .withExperienceLevel(linkToVacancies.getExperienceLevel())
                    .withLinkToVacancy(element.attr("href"))
                    .withVacancyTitle(element.text())
                    .build());
        }
        return parseResult;
    }

}
