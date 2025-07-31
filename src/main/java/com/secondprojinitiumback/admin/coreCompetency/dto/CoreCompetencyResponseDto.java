package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 사용자 응답 결과 조회용 DTO
public class CoreCompetencyResponseDto {
    private Long id;                 // 응답 ID
    private Long assessmentId;       // 평가 ID
    private String assessmentName;   // 평가 이름
    private Long questionId;         // 문항 ID
    private String questionContent;  // 문항 내용
    private Long choiceOptionId;     // 선택한 보기 ID
    private String choiceLabel;      // 선택한 보기 라벨
    private Integer resultScore;     // 응답 점수
    private String completeDate;     // 응답 완료일
    private String resultContent;    // 응답 설명/해석
    private Integer selectCount;     // 선택한 보기 수
}
