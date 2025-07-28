package com.secondprojinitiumback.admin.extracurricular.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "core_competency_category")
public class CoreCompetencyCategory {
    @Id
    @Column(name = "ctgr_id")
    private Long ctgrId;
}
