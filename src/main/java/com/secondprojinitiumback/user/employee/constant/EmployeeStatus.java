package com.secondprojinitiumback.user.employee.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum EmployeeStatus {

    // 교직원 상태 코드
    ACTIVE("10", "재직"),
    ON_LEAVE("20", "휴직"),
    TERMINATED("30", "퇴직");

    private final String code;
    private final String label;

    // 상태 코드로부터 해당 enum을 찾는 메소드
    public static EmployeeStatus fromCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 재적 상태 코드: " + code));
    }

    // 상태 코드로부터 해당 enum의 label을 반환하는 메소드
    public static String labelOf(String code) {
        return fromCode(code).getLabel();
    }
}
