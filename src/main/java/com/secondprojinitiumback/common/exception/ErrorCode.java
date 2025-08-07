package com.secondprojinitiumback.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 값을 확인해주세요."),
    AUTH_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),
    PASSWORD_TOO_SHORT(HttpStatus.BAD_REQUEST, "새 비밀번호는 최소 8자 이상이어야 합니다."),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    UNKNOWN_USER_TYPE(HttpStatus.BAD_REQUEST, "알 수 없는 사용자 유형입니다."),

    // 401 UNAUTHORIZED
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다."),

    // 403 FORBIDDEN
    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "계정이 잠겨있습니다."),

    // 404 NOT_FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    LOGIN_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "로그인 정보를 찾을 수 없습니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    AUTH_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    AUTH_CODE_EXPIRED(HttpStatus.NOT_FOUND, "인증 코드가 만료되었습니다."),
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "학생 정보를 찾을 수 없습니다."),
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "직원 정보를 찾을 수 없습니다."),

    // 500 INTERNAL_SERVER_ERROR
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 발송에 실패했습니다."),
    TEMPORARY_PASSWORD_ISSUE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "임시 비밀번호 발급에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
