package com.vacancy.checker.server.vacancies.page.parser;

import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.provider.AbstractVacancyProvider;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class DjinniParser implements VacanciesHtmlPageParser {

    @Override
    public List<Vacancy> getAllVacancies(AbstractVacancyProvider<String>.VacancyLinkToResponseEntity vacancyLinkToResponseEntity) {
        List<Vacancy> parseResult = new LinkedList<>();
        Elements foundVacancies = Jsoup.parse(Objects.requireNonNull(vacancyLinkToResponseEntity.getGotResponse().getBody()))
                .select("li.list__item a.profile");
        for (var element : foundVacancies) {
            parseResult.add(Vacancy.builder()
                    .withVacancySite(vacancyLinkToResponseEntity.getLinkToVacancies().getVacancySite())
                    .withExperienceLevel(vacancyLinkToResponseEntity.getLinkToVacancies().getExperienceLevel())
                    .withLinkToVacancy(element.attr("href"))
                    .withVacancyTitle(element.text())
                    .build());
        }
        return parseResult;
    }

}
