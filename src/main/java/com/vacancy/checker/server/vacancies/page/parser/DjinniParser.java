package com.vacancy.checker.server.vacancies.page.parser;

import com.vacancy.checker.server.vacancies.model.LinkToVacancies;
import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.provider.VacancyLinkToResponseEntity;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class DjinniParser implements VacanciesHtmlPageParser {

    @Override
    public List<Vacancy> getAllVacancies(VacancyLinkToResponseEntity<String> vacancyLinkToResponseEntity) {
        List<Vacancy> parseResult = new LinkedList<>();
        Elements foundVacancies = Jsoup.parse(Objects.requireNonNull(vacancyLinkToResponseEntity.getGotResponse().getBody()))
                .select("li.list__item a.profile");
        for (var element : foundVacancies) {
            LinkToVacancies linkToVacancies = vacancyLinkToResponseEntity.getLinkToVacancies();
            URI uriLink = URI.create(linkToVacancies.getLink());
            parseResult.add(Vacancy.builder()
                    .withVacancySite(linkToVacancies.getVacancySite())
                    .withExperienceLevel(linkToVacancies.getExperienceLevel())
                    .withLinkToVacancy(uriLink.getScheme() + "://" + uriLink.getAuthority() + element.attr("href"))
                    .withVacancyTitle(element.text())
                    .build());
        }
        return parseResult;
    }

}
