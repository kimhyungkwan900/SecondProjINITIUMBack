package com.secondprojinitiumback.admin.Mileage.domain;

import com.secondprojinitiumback.user.student.domain.Student;
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
    private Long id; // 마일리지 실적 ID

    // 적립/차감 점수 (필수: 적립=양수, 차감=음수)
    @Column(name = "ACC_MLG", nullable = false)
    private Integer accMlg;

    // 생성일시 (DB DEFAULT 사용 or PrePersist로 세팅)
    @Column(name = "CRTN_DT", nullable = false)
    private LocalDateTime createdAt;

    // 취소일시/사유 (선택)
    @Column(name = "CNCL_DT")
    private LocalDateTime canceledAt;

    @Column(name = "CNCL_RSN_CN", columnDefinition = "TEXT")
    private String cancelReason;

    // 마일리지 항목 (차감 시 NULL 허용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MLG_ALTCL_ID", nullable = false)
    private MileageItem mileageItem;

    // 배점 정책 (선택)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "MLG_SCOR_PLCY_ID", nullable = true)
    private ScorePolicy scorePolicy;

    // 장학금 신청 (차감 시 보통 연결, 없을 수도 있음)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "SCHLR_APLY_ID", nullable = true)
    private ScholarshipApply scholarshipApply;

    // 학생 (FK: STDNT_INFO.STDNT_NO) — 필수
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STDNT_NO", referencedColumnName = "STDNT_NO", nullable = false)
    private Student student;

    // 취소 여부
    public boolean isCanceled() {
        return canceledAt != null;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
