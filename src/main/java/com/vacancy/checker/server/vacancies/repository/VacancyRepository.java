package com.vacancy.checker.server.vacancies.repository;

import com.vacancy.checker.server.vacancies.entity.VacancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@Transactional
public interface VacancyRepository extends JpaRepository<VacancyEntity, UUID> {

    @Modifying
    @Query("delete from VacancyEntity v where v.detectionDate < :date")
    void deleteAllOlderThan(@Param("date") LocalDateTime date);

}
