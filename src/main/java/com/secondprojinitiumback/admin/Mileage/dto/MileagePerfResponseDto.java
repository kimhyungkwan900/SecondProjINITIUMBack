package com.secondprojinitiumback.admin.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MileagePerfResponseDto {

    private Long id;                  // 실적 ID

    private String studentNo;        // 학번
    private String name;      // 이름
    private String schoolSubjectName;   // 학과명

    private Integer accMlg;           // 적립 마일리지
    private LocalDateTime createdAt;  // 등록일
    private LocalDateTime canceledAt; // 취소일 (nullable)
    private String cancelReason;      // 취소사유

    private Long mileageItemId;       // 마일리지 항목 ID
    private String mileageItemCode;   // 마일리지 항목 코드
    private String eduNm; // 비교과 프로그램명

    private Long scorePolicyId;       // 배점 정책 ID
    private String scoreCriteria;     // 배점 조건

    private Long scholarshipApplyId;  // 장학금 신청 ID (nullable)

    // 엔티티 → DTO 변환용 정적 메서드
    public static MileagePerfResponseDto from(MileagePerf perf) {
        return MileagePerfResponseDto.builder()
                .id(perf.getId())

                .studentNo(perf.getStudent().getStudentNo())
                .name(perf.getStudent().getName())
                .schoolSubjectName(perf.getStudent().getSchoolSubject().getSubjectName())

                .accMlg(perf.getAccMlg())
                .createdAt(perf.getCreatedAt())
                .canceledAt(perf.getCanceledAt())
                .cancelReason(perf.getCancelReason())

                .mileageItemId(perf.getMileageItem().getId())
                .mileageItemCode(perf.getMileageItem().getItemCode())
                .eduNm(perf.getMileageItem().getProgram().getEduNm())

                .scorePolicyId(perf.getScorePolicy().getId())
                .scoreCriteria(perf.getScorePolicy().getScoreCriteria())

                //.scholarshipApplyId(perf.getScholarshipApply() != null ? perf.getScholarshipApply().getId() : null)
                .build();
    }
}
