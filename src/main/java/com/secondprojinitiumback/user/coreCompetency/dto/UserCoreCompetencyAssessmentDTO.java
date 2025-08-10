package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCoreCompetencyAssessmentDTO {

    private Long id;
    private String assessmentName; // 평가 이름
    private String startDate;      // 시작일
    private String endDate;        // 종료일


}
