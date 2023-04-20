package com.vacancy.checker.server.vacancies.storage;

import com.vacancy.checker.server.service.TimeService;
import com.vacancy.checker.server.vacancies.entity.VacancyEntity;
import com.vacancy.checker.server.vacancies.model.Vacancy;
import com.vacancy.checker.server.vacancies.repository.VacancyRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class VacanciesStorageImpl implements KnownVacanciesStorage{

    private final VacancyRepository vacancyRepository;

    private final TimeService timeService;

    public VacanciesStorageImpl(VacancyRepository vacancyRepository,
                                TimeService timeService) {
        this.vacancyRepository = vacancyRepository;
        this.timeService = timeService;
    }

    @Override
    public Set<Vacancy> findAll() {
        return vacancyRepository.findAll().stream()
                .map(vacancyEntityToVacancyMapper())
                .collect(Collectors.toSet());
    }

    private static Function<VacancyEntity, Vacancy> vacancyEntityToVacancyMapper() {
        return vacancyEntity -> Vacancy.builder()
                .withVacancyTitle(vacancyEntity.getVacancyTitle())
                .withLinkToVacancy(vacancyEntity.getLinkToVacancy())
                .withVacancySite(vacancyEntity.getVacancySite())
                .withExperienceLevel(vacancyEntity.getExperienceLevel())
                .build();
    }

    @Override
    public void addAll(Set<Vacancy> vacancies) {
        vacancyRepository.saveAll(vacancies.stream().map(vacancyToVacancyEntityMapper()).toList());
    }

    private Function<Vacancy, VacancyEntity> vacancyToVacancyEntityMapper() {
        return vacancy -> VacancyEntity.builder()
                .withVacancyTitle(vacancy.getVacancyTitle())
                .withLinkToVacancy(vacancy.getLinkToVacancy())
                .withVacancySite(vacancy.getVacancySite())
                .withExperienceLevel(vacancy.getExperienceLevel())
                .withDetectionDate(timeService.now())
                .build();
    }
}
