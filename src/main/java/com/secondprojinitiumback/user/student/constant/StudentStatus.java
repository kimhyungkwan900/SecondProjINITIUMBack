package com.secondprojinitiumback.user.student.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum StudentStatus {

    // 학적 상태 코드
    ENROLLED("10", "재학"),
    LEAVE("20", "휴학"),
    ACADEMIC_PROBATION("30", "제적"),
    WITHDRAWN("40", "수료"),
    GRADUATED("50", "졸업");

    private final String code;
    private final String label;


    // 학적 상태 코드로부터 해당 enum을 찾는 메소드
    public static StudentStatus fromCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 학적 상태 코드: " + code));
    }

    // 학적 상태 코드로부터 해당 enum의 label을 반환하는 메소드
    public static String labelOf(String code) {
        return fromCode(code).getLabel();
    }
}