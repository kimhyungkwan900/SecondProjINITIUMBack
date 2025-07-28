package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosisRequestDto {
    private String qestrnSeq;
    private String trgetSe;
    private Map<String, String> answers; // 문항별 응답 코드
    private String gender;
    private String school;
    private String grade;
    private String startDtm;

}