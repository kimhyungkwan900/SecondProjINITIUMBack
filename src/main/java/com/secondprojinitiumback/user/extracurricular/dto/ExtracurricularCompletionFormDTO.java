package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularCompletionFormDTO {
    private Long eduFnshId; // 완료 ID
    private ExtracurricularApply extracurricularApply; // 신청 정보
    private String eduFnshYn; // 완료 여부 (Y/N)
    private LocalDateTime eduFnshDt; // 완료 일시
}
