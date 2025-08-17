package com.secondprojinitiumback.admin.extracurricular.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtracurricularSurveyParticipationStatusDTO {
    private Long eduMngId; // 비교과 프로그램 ID
    private String programName; // 프로그램 이름
    private String empNo; // 담당 교직원 번호
    private String surveyTitle; // 설문 제목
    private int totalApplicants; // 프로그램 신청(승인) 학생 수 (전체 대상자)
    private int totalResponded; // 설문 응답 완료 학생 수
    private double participationRate; // 참여율
}
