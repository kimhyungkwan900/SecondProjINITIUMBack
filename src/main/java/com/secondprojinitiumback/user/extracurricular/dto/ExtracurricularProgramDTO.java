package com.secondprojinitiumback.user.extracurricular.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularProgramDTO {
    private Long eduMngId; // 프로그램 ID
    private String eduMngNm; // 프로그램 이름
    private String eduMngCn; // 프로그램 설명
    private String eduMngStts; // 프로그램 상태
    private int eduMlg; // 프로그램 최대 인원
    private int eduAplyCnt; // 신청 인원 수


}
