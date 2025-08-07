package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularApplyFormDTO { // 신청 폼 DTO
    private Long eduAplyId; // 신청 ID
    private ExtracurricularProgramDTO extracurricularProgram; // 프로그램 정보
    private String eduAplyCn; // 신청 작성 내용
    private AprySttsNm aprySttsNm; // 신청 상태 (ENUM)
    private LocalDateTime eduAplyDt; // 신청 일시
}
