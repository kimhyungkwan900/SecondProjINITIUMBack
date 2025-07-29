package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosisRequestDto {

    /**
     * 학생 번호 (STDNT_NO)
     * 외부 진단검사 결과를 저장할 때 Student 엔티티와 연계
     */
    private String studentNo;

    /**
     * 외부 검사 API 코드 (커리어넷 qestrnSeq 값)
     */
    private String qestrnSeq;

    /**
     * 대상 코드 (trgetSe)
     */
    private String trgetSe;

    /**
     * 문항별 응답 코드 (key: 문항번호, value: 선택값)
     */
    private Map<String, String> answers;

    /**
     * 성별
     */
    private String gender;

    /**
     * 학교명
     */
    private String school;

    /**
     * 학년
     */
    private String grade;

    /**
     * 시작일시
     */
    private String startDtm;
}
