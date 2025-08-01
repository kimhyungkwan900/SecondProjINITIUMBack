package com.secondprojinitiumback.admin.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.ScorePolicy;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScorePolicyResponseDto {
    private Long id; // 정책 ID
    private String scoreCriteria; // 조건 설명
    private Integer requiredAttendance; // 출석 조건
    private Double scoreRate; // 지급 비율
    private String useYn; // 사용 여부
    private LocalDateTime createdAt; // 등록일

    private Long eduMngId; // 비교과 프로그램 ID
    private String eduNm; // 비교과 프로그램
    private Integer eduMlg; //마일리지

    private Long mileageItemId; // 항목 ID
    private String mileageItemCode; // 항목 코드

    // Entity → DTO 변환 생성자 (내부 전환용)
    public static ScorePolicyResponseDto from(ScorePolicy policy) {
        return ScorePolicyResponseDto.builder()
                .id(policy.getId())
                .scoreCriteria(policy.getScoreCriteria())
                .requiredAttendance(policy.getRequiredAttendance())
                .scoreRate(policy.getScoreRate())
                .useYn(policy.getUseYn())
                .createdAt(policy.getCreatedAt())
                .eduMngId(policy.getProgram().getEduMngId())
                .eduNm(policy.getProgram().getEduNm())
                .eduMlg(policy.getProgram().getEduMlg())
                .mileageItemId(policy.getMileageItem().getId())
                .mileageItemCode(policy.getMileageItem().getItemCode())
                .build();
    }

}
