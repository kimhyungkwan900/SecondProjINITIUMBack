package com.secondprojinitiumback.user.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserMileageHistoryDto {

    private String type; // 지급 or 차감
    private String description; // 비교과명 또는 장학금 신청
    private String change; // 마일리지 변화량 (ex : +30 / -40)
    private double totalScore; // 해당 시점 누적 점수
    private LocalDateTime createdAt;

    // 지급 내역 변환
    public static UserMileageHistoryDto from(MileagePerf perf, double totalScore




       ) {
        return UserMileageHistoryDto.builder()
                .type("지급")
                .description(perf.getMileageItem().getProgram().getEduNm()) //비교과 프로그램명
                .change("+" + perf.getAccMlg()) //지급된 마일리지
                .totalScore(totalScore)
                .createdAt(perf.getCreatedAt())
                .build();
    }

    // 차감 내역 변환
    public static UserMileageHistoryDto from(ScholarshipApply apply, double totalScore) {
        return UserMileageHistoryDto.builder()
                .type("차감")
                .description("장학금 신청")
                .change("-" + apply.getAccumulatedMileage()) //차감된 마일리지
                .totalScore(totalScore)
                .createdAt(apply.getApplyDate())
                .build();
    }
}

