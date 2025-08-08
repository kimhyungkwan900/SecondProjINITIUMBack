package com.secondprojinitiumback.user.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserScholarshipStatusDto {

    private LocalDateTime applyDate;      // 신청일자
    private int accumulatedMileage;       // 신청한 마일리지
    private int calculatedAmount;         // 환산 금액
    private String state;                 // 신청 상태명(신청, 승인, 반려)

    // 엔티티 → DTO로 변환하는 메서드
    public static UserScholarshipStatusDto from(ScholarshipApply apply) {
        return UserScholarshipStatusDto.builder()
                .applyDate(apply.getApplyDate())
                .accumulatedMileage(apply.getAccumulatedMileage())
                .calculatedAmount(apply.getAccumulatedMileage() * 100) // 마일리지 1점당 100원
                .state(apply.getStateCode().getCodeName()) // 공통 코드명
                .build();
    }
}

