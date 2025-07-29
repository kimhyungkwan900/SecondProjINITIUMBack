package com.secondprojinitiumback.admin.coreCompetency.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "core_competency_category")
public class CoreCompetencyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CTGR_ID")
    private int id; // 핵심역량 카테고리 ID (PK)

    @Column(name = "CTGR_NM")
    private String coreCategoryName; // 핵심역량 카테고리 이름

    @Column(name = "CTGR_CN")
    private String coreCategoryNote; // 핵심역량 카테고리 설명
}

