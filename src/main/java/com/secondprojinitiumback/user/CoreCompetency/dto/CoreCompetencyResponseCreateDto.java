package com.secondprojinitiumback.user.CoreCompetency.dto;

// 사용자 응답 등록 요청용 DTO
public class CoreCompetencyResponseCreateDto {
    private Long assessmentId;        // 평가 ID
    private Long questionId;          // 문항 ID
    private Long selectedOptionId;    // 선택한 보기 ID
    private String completeDate;      // 응답 완료일
    private Integer resultScore;      // 점수
    private String resultContent;     // 응답 해석 또는 설명
    private Integer selectCount;      // 선택 수
}
