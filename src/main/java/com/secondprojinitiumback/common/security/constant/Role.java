package com.secondprojinitiumback.common.security.constant;

import lombok.Getter;

@Getter
public enum Role {
    // 권한 코드
    STUDENT("S", 10),
    EMPLOYEE("E", 20),
    ADMIN("A", 30);

    private final String userType;
    private final int code;

    Role(String userType, int code) {
        this.userType = userType;
        this.code = code;
    }

    // 사용자 타입에 해당하는 Role을 반환하는 메서드
    public static Role fromUserType(String userType) {
        for (Role role : values()) {
            if (role.userType.equals(userType)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown userType: " + userType);
    }
}