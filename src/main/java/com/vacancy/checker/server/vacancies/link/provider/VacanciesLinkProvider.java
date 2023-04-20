package com.vacancy.checker.server.vacancies.link.provider;


import com.vacancy.checker.server.vacancies.model.LinkToVacancies;
import com.vacancy.checker.server.vacancies.model.VacancySite;

import java.util.List;

public interface VacanciesLinkProvider {

    List<LinkToVacancies> getLinksRelatedWithSite(VacancySite site);

}
