package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// 문항 등록/수정 요청을 위한 DTO
public class CoreCompetencyQuestionCreateDto {
    private Long Id;                // 문항 ID
    private Long assessmentId;       // 진단평가 ID
    private Integer questionNo;      // 문항 번호
    private String questionName;     // 문항 내용
    private Integer displayOrder;    // 화면 출력 순서
    private Integer answerAllowCount;// 허용 응답 개수
    private String isCommonCode;        // 공통문항 여부
}
