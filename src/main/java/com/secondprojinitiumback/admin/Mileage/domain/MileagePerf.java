package com.secondprojinitiumback.admin.Mileage.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MLG_PERF")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MileagePerf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MLG_PERF_ID")
    private Long id; //마일리지 실적 ID

    @Column(name = "STDNT_NO", length = 10, nullable = false)
    private String studentNo; // 학번

    @Column(name = "ACC_MLG", nullable = false)
    private Integer accMlg; //적립된 마일리지

    @Column(name = "CRTN_DT", nullable = false)
    private LocalDateTime createdAt; // 실적 등록일시

    @Column(name = "CNCL_DT")
    private LocalDateTime canceledAt; // 취소일시

    @Column(name = "CNCL_RSN_CN", columnDefinition = "TEXT")
    private String cancelReason; // 취소사유

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MLG_ALTCL_ID", nullable = false)
    private MileageItem mileageItem; // 마일리지 항목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MLG_SCOR_PLCY_ID", nullable = false)
    private ScorePolicy scorePolicy; // 배점 정책

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "SCHLR_APLY_ID")
//    private ScholarshipApply scholarshipApply; // 장학금 신청 ID






}
