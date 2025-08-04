package com.secondprojinitiumback.admin.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ScholarshipApplyResponseDto {

    private Long id;                         // 신청 ID
    private String studentNo;                // 학번
    private String studentName;              // 학생 이름
    private String schoolSubjectName;        // 학과명

    private String accountNo;                // 계좌번호
    private String bankCode;                 // 은행 코드
    private String bankName;                 // 은행 이름

    private int accumulatedMileage;          // 누적 마일리지 점수
    private BigDecimal paymentAmount;        // 지급 금액

    private String stateCode;                // 상태 코드
    private String stateName;                // 상태 이름

    private LocalDateTime applyDate;         // 신청일
    private LocalDateTime approveDate;       // 승인일
    private LocalDateTime rejectDate;        // 반려일
    private String rejectReason;             // 반려 사유

    // 엔티티 → DTO 변환
    public static ScholarshipApplyResponseDto from(ScholarshipApply entity) {
        return ScholarshipApplyResponseDto.builder()
                .id(entity.getId())
                .studentNo(entity.getStudent().getStudentNo())
                .studentName(entity.getStudent().getName())
                .schoolSubjectName(entity.getStudent().getSchoolSubject().getSubjectName())

                .accountNo(entity.getBankAccount() != null
                        ? entity.getBankAccount().getAccountNo()
                        : null)

                .bankCode(entity.getBankAccount() != null
                        && entity.getBankAccount().getBankCode() != null
                        && entity.getBankAccount().getBankCode().getId() != null
                        ? entity.getBankAccount().getBankCode().getId().getCode()
                        : null)

                .bankName(entity.getBankAccount() != null
                        && entity.getBankAccount().getBankCode() != null
                        ? entity.getBankAccount().getBankCode().getCodeName()
                        : null)

                .accumulatedMileage(entity.getAccumulatedMileage())
                .paymentAmount(entity.getPaymentAmount())

                .stateCode(entity.getStateCode() != null
                        && entity.getStateCode().getId() != null
                        ? entity.getStateCode().getId().getCode()
                        : null)
                .stateName(entity.getStateCode() != null
                        ? entity.getStateCode().getCodeName()
                        : null)

                .applyDate(entity.getApplyDate())
                .approveDate(entity.getApproveDate())
                .rejectDate(entity.getRejectDate())
                .rejectReason(entity.getRejectReason())
                .build();
    }
}

