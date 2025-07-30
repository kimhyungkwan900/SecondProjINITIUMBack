package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularSurveyResponseDTO {
    private Long srvyRspnsId; // 설문 응답 ID
    private ExtracurricularSurvey extracurricularSurvey;  // 설문 정보
    private StdntInfo stdntInfo; // 학생 정보
    private String srvyRspnsCn; // 설문 응답 내용
    private LocalDateTime srvyRspnsDt; // 설문 응답 일시
    private int srvyDgstfnScr; // 설문 응답 점수

}
