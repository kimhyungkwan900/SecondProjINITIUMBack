package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExtracurricularCompletionDTO {
    private Long eduFnshId;
    private ExtracurricularApply extracurricularApply; // 신청 정보
    private String eduFnshYn; // 완료 여부
    private LocalDateTime eduFnshDt;
}
