package com.linksphere.user_service.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.linksphere.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEducation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "degree_name")
    private String degreeName;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "grade")
    private String grade;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
