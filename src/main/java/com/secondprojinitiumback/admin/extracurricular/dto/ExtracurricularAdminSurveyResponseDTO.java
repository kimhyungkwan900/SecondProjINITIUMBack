package com.secondprojinitiumback.admin.extracurricular.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularAdminSurveyResponseDTO {
    private Long srvyRspnsId;
    private int srvyDgstfnScr;
    private String studentNo; // 학번
    private String name; // 이름
    private String surveyResponseContent;
}
