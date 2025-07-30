package com.secondprojinitiumback.user.extracurricular.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "extracurricular_apply")
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_aply_id")
    private Long eduAplyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private StdntInfo stdntInfo; // 학생 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_mng_id", nullable = false)
    private ExtracurricularProgram extracurricularProgram;

    @Column(name = "edu_aply_cn", nullable = false)
    private String eduAplyCn; // 신청 내용

    @Column(name = "edu_aply_dt", nullable = false)
    private LocalDateTime eduAplyDt;

    @Column(name = "aprv_stts_nm")
    private AprySttsNm aprySttsNm; // 승인 여부 (Y/N)

}

