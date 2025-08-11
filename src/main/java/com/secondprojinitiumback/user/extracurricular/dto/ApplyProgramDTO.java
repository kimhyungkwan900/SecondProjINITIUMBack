package com.secondprojinitiumback.user.extracurricular.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyProgramDTO {
    private Long eduAplyId;

    private String eduNm; //  프로그램 이름
    private LocalDate eduEndYmd; // 교육 마지막 날
    private String cndCn; //프로그램 수료 조건

    private double attendance; //출석률
    private Boolean surveyYn; //만족도 설문 작성 여부

    private Long eduFnshId; // 수료 ID
    private String eduFnshYn; // 수료 여부 (Y/N)

}
