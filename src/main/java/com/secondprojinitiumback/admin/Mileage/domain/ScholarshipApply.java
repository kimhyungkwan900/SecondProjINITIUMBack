package com.secondprojinitiumback.admin.Mileage.domain;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SCHLR_APLY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ScholarshipApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHLR_APLY_ID")
    private Long id; // 장학금 신청 고유 ID

    @Column(name = "ACCU_MLG_SCR", nullable = false)
    private int accumulatedMileage; // 신청 시점의 누적 마일리지 점수

    @Column(name = "APLY_DT", nullable = false)
    private LocalDateTime applyDate; // 신청 일자

    @Column(name = "APRV_DT")
    private LocalDateTime approveDate; // 승인 일자 (승인 시만 값 존재)

    @Column(name = "RJCT_DT")
    private LocalDateTime rejectDate; // 반려 일자 (반려 시만 값 존재)

    @Column(name = "RJCT_RSN_CN", columnDefinition = "TEXT")
    private String rejectReason; // 반려 사유 (반려 시에 기재됨)

    @Column(name = "SCHLR_PMT_AMT", precision = 10, scale = 2)
    private BigDecimal paymentAmount; // 장학금 지급 금액

    // 상태 코드 (신청, 승인, 반려, 지급 등 상태를 나타냄)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "STATE_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "CD_SE", referencedColumnName = "CD_SE")
    })
    private CommonCode stateCode; // 코드 테이블에서 상태를 관리 (예: ML001, CD: 1/2/3/4)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_NO") // 학생 테이블의 학번을 FK로 사용
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTNO") // 계좌번호를 외래키로 사용
    private BankAccount bankAccount;
}

