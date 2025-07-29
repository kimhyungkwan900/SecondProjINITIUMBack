package com.secondprojinitiumback.user.extracurricular.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularApplyFormDTO { // 신청 폼 DTO
    private Long eduAplyId; // 신청 ID
    private Long eduMngId; // 프로그램 정보
    private String eduAplyCn; // 신청 작성 내용
    private LocalDateTime eduAplyDt; // 신청 일시
}
