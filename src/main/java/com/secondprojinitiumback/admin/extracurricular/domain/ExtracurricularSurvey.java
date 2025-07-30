package com.secondprojinitiumback.admin.extracurricular.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="extracurricular_survey")
public class ExtracurricularSurvey {

    @Id
    @Column(name = "srvy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srvyId; // 설문 ID

    @OneToOne
    @JoinColumn(name = "edu_mng_id")
    private ExtracurricularProgram extracurricularProgram; // 비교과 프로그램 ID

    @Column(name = "srvy_ttl")
    private String srvyTtl; // 설문 제목

    @Column(name = "srvy_qitem_cn")
    private String srvyQitemCn; // 설문 문항 내용

    @Column(name = "srvy_bgng_dt")
    private String srvyBgngDt; // 설문 시작일

    @Column(name = "srvy_end_dt")
    private String srvyEndDt; // 설문 종료일


}
