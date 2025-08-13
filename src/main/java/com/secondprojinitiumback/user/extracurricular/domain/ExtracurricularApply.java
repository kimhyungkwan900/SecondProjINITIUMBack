package com.secondprojinitiumback.user.extracurricular.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "extracurricular_apply")
@Setter
@Getter
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
    private Student student; // 학생 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_mng_id", nullable = false)
    private ExtracurricularProgram extracurricularProgram;

    @Column(name = "edu_aply_cn", nullable = false)
    private String eduAplyCn; // 신청 내용

    @Column(name = "edu_aply_dt", nullable = false)
    private LocalDateTime eduAplyDt;

    @Column(name = "aprv_stts_nm")
    @Enumerated(EnumType.STRING)
    private AprySttsNm aprySttsNm;

    @Column(name = "del_yn")
    private String delYn; // 삭제 여부 (Y/N) - 논리 삭제를 위한 필드

    @OneToOne(mappedBy = "extracurricularApply", fetch = FetchType.LAZY)
    private ExtracurricularCompletion extracurricularCompletion;

}

