package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppliedExtracurricularProgramDTO {
    private Long eduMngId;                  // 비교과 프로그램 관리 ID
    private String eduNm;                   // 비교과 프로그램 이름
    private String ctgryNm;                 // 비교과 프로그램 카테고리 이름
    private LocalDateTime eduAplyBgngDt;    // 비교과 프로그램 신청 시작 일시
    private LocalDateTime eduAplyEndDt;     // 비교과 프로그램 신청 종료 일시
    private AprySttsNm aprySttsNm;          // 신청 상태 이름
}
