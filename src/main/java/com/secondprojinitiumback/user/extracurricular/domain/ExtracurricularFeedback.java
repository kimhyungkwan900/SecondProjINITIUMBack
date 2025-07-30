package com.secondprojinitiumback.user.extracurricular.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "extracurricular_feedback")
public class ExtracurricularFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_feedback_id")
    private Long feedbackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_mng_id")
    private ExtracurricularProgram extracurricularProgram; // 프로그램 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no")
    private StdntInfo stdntInfo; // 학생 정보

    @Column(name = "edu_feedback_ttl")
    private String eduFeedbackTtl; // 피드백 제목

    @Column(name = "edu_feedback_cn")
    private String eduFeedbackCn; // 피드백 내용

    @Column(name = "wrt_dt")
    private LocalDateTime wrtDt; // 작성일

    @Column(name = "update_dt")
    private LocalDateTime updateDt; // 수정일

    @Column(name = "info_rls_yn")
    private String infoRlsYn; // 정보 공개 여부 (Y/N)

}
