package com.secondprojinitiumback.admin.coreCompetency.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sub_competency_category")
public class SubCompetencyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STGR_ID")
    private Long id; // 하위 역량 ID

    @Column(name = "STGR_NM")
    private String subCategoryName; // 하위 역량명

    @Column(name = "STGR_CN")
    private String subCategoryNote; // 하위 역량 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CTGR_ID", nullable = false)
    private CoreCompetencyCategory coreCompetencyCategory;

}
