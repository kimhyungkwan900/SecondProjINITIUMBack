package com.secondprojinitiumback.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("M", "남성"),
    FEMALE("F", "여성"),
    UNKNOWN("U", "알 수 없음"); // 또는 "기타" 등, CO0001의 세부 코드에 따라 정의

    private final String code;
    private final String description;

    public static Gender fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (Gender gender : Gender.values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 학적 상태 코드: " + code);
    }
}