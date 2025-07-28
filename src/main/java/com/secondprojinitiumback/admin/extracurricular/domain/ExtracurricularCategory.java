package com.secondprojinitiumback.admin.extracurricular.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "extracurricular_category")
public class ExtracurricularCategory {

    @Id
    @Column(name = "ctgry_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ctgry_id;

    @Column(name = "stgr_id", nullable = false)
    private Long stgrId;

    @Column(name = "ctgry_nm", nullable = false)
    private String ctgryNm;

    @Column(name = "data_crt_dt")
    private Date dataCrtDt;

    @Column(name = "Ctgry_Use_YN")
    private String ctgryUseYn;

}
