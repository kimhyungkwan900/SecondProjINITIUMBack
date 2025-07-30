package com.secondprojinitiumback.user.extracurricular.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class ExtracurricularSurveyResponse {
    @Id
    @Column(name = "srvy_rspns_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srvyRspnsId; // 설문 응답 ID

    @ManyToOne
    @JoinColumn(name = "srvy_id")
    private ExtracurricularSurvey extracurricularSurvey; // 설문 정보

    @ManyToOne
    @JoinColumn(name = "stdnt_no")
    private StdntInfo stdntInfo; // 학생 번호

    @Column(name = "srvy_rspns_cn")
    private String surveyResponseContent; // 설문 응답 내용

    @Column(name = "srvy_rspns_dt")
    private LocalDateTime surveyResponseDate; // 설문 응답 날짜

    @Column(name = "srvy_dgstfn_scr")
    private int srvyDgstfnScr; // 설문 응답 점수
}
