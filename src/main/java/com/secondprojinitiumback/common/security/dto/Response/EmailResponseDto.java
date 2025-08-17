package com.secondprojinitiumback.common.security.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailResponseDto {
    private final String email;       // 실제 전송용(프론트는 보관만)
    private final String maskedEmail; // 화면 표시용
}