package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 평가 결과 정보 조회용 DTO
public class CoreCompetencyResultDto {
    private Long id;                 // 결과 ID
    private String studentNo;
    private String name;
    private String gender;
    private String department;
    private String schoolYear;
    private String status;
    private String completeDate;

    // 문항번호별 응답 라벨 (ex: "보통이다")
    private Map<Integer, String> responseLabelByQuestionNo;
}
