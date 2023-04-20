package com.vacancy.checker.server.vacancies.provider;

import com.vacancy.checker.server.vacancies.model.LinkToVacancies;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class VacancyLinkToResponseEntity<T> {

    private final LinkToVacancies linkToVacancies;
    private final ResponseEntity<T> gotResponse;

}
