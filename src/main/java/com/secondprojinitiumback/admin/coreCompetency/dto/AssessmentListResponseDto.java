package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

//프론트엔드로 평가 목록을 보낼 때 사용하는 DTO
public class AssessmentListResponseDto {
    private Long assessmentId;
    private String assessmentNo; // 진단번호
    private String assessmentName; // 평가명


    private boolean isCompleted; // <<< [추가] 학생의 응시 완료 여부 필드

    // 생성자 또는 빌더 수정
    public AssessmentListResponseDto(CoreCompetencyAssessment assessment, boolean isCompleted) {
        this.assessmentId = assessment.getId();
        this.assessmentName = assessment.getAssessmentName();
        this.isCompleted = isCompleted; // 응시 여부 값을 받아서 설정
    }
}