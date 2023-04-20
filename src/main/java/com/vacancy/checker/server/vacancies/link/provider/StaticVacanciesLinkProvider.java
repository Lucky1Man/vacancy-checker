package com.vacancy.checker.server.vacancies.link.provider;

import com.vacancy.checker.server.vacancies.model.ExperienceLevel;
import com.vacancy.checker.server.vacancies.model.LinkToVacancies;
import com.vacancy.checker.server.vacancies.model.VacancySite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StaticVacanciesLinkProvider implements VacanciesLinkProvider {

    private static final Map<VacancySite, List<LinkToVacancies>> siteToItsVacanciesLink;

    static {
        siteToItsVacanciesLink = Map.ofEntries(
                new Map.Entry<>() {
                    @Override
                    public VacancySite getKey() {
                        return VacancySite.DJINNI;
                    }

                    @Override
                    public List<LinkToVacancies> getValue() {
                        return List.of(
                                LinkToVacancies.builder()
                                        .withExperienceLevel(ExperienceLevel.ONE_YEAR)
                                        .withVacancySite(VacancySite.DJINNI)
                                        .withLink("https://djinni.co/jobs/?primary_keyword=Java&exp_level=1y")
                                        .build(),
                                LinkToVacancies.builder()
                                        .withExperienceLevel(ExperienceLevel.NO_EXPERIENCE)
                                        .withVacancySite(VacancySite.DJINNI)
                                        .withLink("https://djinni.co/jobs/?primary_keyword=Java&exp_level=no_exp")
                                        .build()
                        );
                    }

                    @Override
                    public List<LinkToVacancies> setValue(List<LinkToVacancies> value) {
                        throw new UnsupportedOperationException();
                    }
                }
        );
    }

    @Override
    public List<LinkToVacancies> getLinksRelatedWithSite(VacancySite site) {
        return siteToItsVacanciesLink.get(site);
    }
}
