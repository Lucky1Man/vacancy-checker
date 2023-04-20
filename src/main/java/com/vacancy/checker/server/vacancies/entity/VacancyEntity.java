package com.vacancy.checker.server.vacancies.entity;

import com.vacancy.checker.server.vacancies.model.ExperienceLevel;
import com.vacancy.checker.server.vacancies.model.VacancySite;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "VacancyEntity")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "vacancies"
)
@Builder(setterPrefix = "with")
public class VacancyEntity {

    @Id
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="BINARY(16) NOT NULL"
    )
    @GeneratedValue
    private UUID id;

    @Column(
            name = "link_to_vacancy",
            nullable = false,
            columnDefinition = "varchar(500)"
    )
    private String linkToVacancy;

    @Column(
            name = "vacancy_title",
            columnDefinition = "varchar(255)"
    )
    private String vacancyTitle;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "experience_level"
    )
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "vacancy_site"
    )
    private VacancySite vacancySite;

    @Column(name = "detection_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime detectionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VacancyEntity that = (VacancyEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
