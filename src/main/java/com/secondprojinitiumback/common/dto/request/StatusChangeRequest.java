package com.secondprojinitiumback.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusChangeRequest {

    @NotBlank(message = "상태 코드는 필수 입력값입니다.")
    private String statusCode;
}
