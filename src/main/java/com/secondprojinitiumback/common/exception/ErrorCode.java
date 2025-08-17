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
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다."),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."),
    UNKNOWN_USER_TYPE(HttpStatus.BAD_REQUEST, "알 수 없는 사용자 유형입니다."),
    BANK_ACCOUNT_INACTIVE(HttpStatus.BAD_REQUEST, "사용 중지된 계좌입니다."),
    INVALID_ACCOUNT_TYPE(HttpStatus.BAD_REQUEST, "허용되지 않는 계좌 유형입니다."),
    PASSWORD_CHANGE_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호 변경이 필요합니다."),

    // 401 UNAUTHORIZED
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다."),

    // 403 FORBIDDEN
    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "계정이 잠겨있습니다."),
    ACCOUNT_DISABLED(HttpStatus.FORBIDDEN, "계정이 잠겨있습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    BANK_ACCOUNT_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "본인 계좌만 등록할 수 있습니다."),

    // == Consult ==
    ONLY_APPLICANT_CAN_CANCEL(HttpStatus.FORBIDDEN, "신청자 본인만 취소할 수 있습니다."),

    // 404 NOT_FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    LOGIN_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "로그인 정보를 찾을 수 없습니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    AUTH_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    AUTH_CODE_EXPIRED(HttpStatus.NOT_FOUND, "인증 코드가 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "학생 정보를 찾을 수 없습니다."),
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "직원 정보를 찾을 수 없습니다."),
    BANK_ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "계좌 정보를 찾을 수 없습니다."),
    COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "공통 코드를 찾을 수 없습니다."),
    SCHOOL_SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "학과 정보를 찾을 수 없습니다."),
    UNIVERSITY_NOT_FOUND(HttpStatus.NOT_FOUND, "대학 정보를 찾을 수 없습니다."),

    // == Extracurricular ==
    APPLY_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "신청 정보를 찾을 수 없습니다."),
    PROGRAM_NOT_FOUND(HttpStatus.NOT_FOUND, "비교과 프로그램을 찾을 수 없습니다."),
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND, "설문 정보를 찾을 수 없습니다."),

    // == Consult ==
    CONSULTATION_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "상담 정보를 찾을 수 없습니다."),
    CONSULTATION_KIND_NOT_FOUND(HttpStatus.NOT_FOUND, "상담 종류를 찾을 수 없습니다."),
    CONSULTATION_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "상담 일정을 찾을 수 없습니다."),

    // == Mileage ==
    SCHOLARSHIP_APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "장학금 신청 정보를 찾을 수 없습니다."),

    // == Core Competency / Diagnostic ==
    ASSESSMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "진단 정보를 찾을 수 없습니다."),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "문항 정보를 찾을 수 없습니다."),
    DIAGNOSTIC_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "진단 결과 정보를 찾을 수 없습니다."),
    IDEAL_TALENT_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "인재상 정보를 찾을 수 없습니다."),
    CORE_COMPETENCY_NOT_FOUND(HttpStatus.NOT_FOUND, "핵심역량 카테고리를 찾을 수 없습니다."),
    SUB_COMPETENCY_NOT_FOUND(HttpStatus.NOT_FOUND, "하위역량 카테고리를 찾을 수 없습니다."),
    RESPONSE_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "선택지 정보를 찾을 수 없습니다."),

    // 409 CONFLICT
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),

    // == Core Competency ==
    CORE_COMPETENCY_NAME_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 핵심역량명입니다."),
    SUB_COMPETENCY_NAME_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 하위역량명입니다."),
    ASSESSMENT_ALREADY_RESPONDED(HttpStatus.CONFLICT, "이미 응답한 진단입니다."),

    // == Extracurricular ==
    PROGRAM_ALREADY_APPLIED(HttpStatus.CONFLICT, "이미 신청한 비교과 프로그램입니다."),
    SURVEY_ALREADY_SUBMITTED(HttpStatus.CONFLICT, "이미 제출한 설문입니다."),

    // == Consult ==
    CONSULTATION_ALREADY_APPLIED(HttpStatus.CONFLICT, "이미 신청한 상담입니다."),
    CONSULTATION_ALREADY_COMPLETED(HttpStatus.CONFLICT, "이미 완료된 상담입니다."),
    CONSULTATION_ALREADY_CANCELLED(HttpStatus.CONFLICT, "이미 취소된 상담입니다."),

    // == Mileage ==
    SCHOLARSHIP_ALREADY_PROCESSED(HttpStatus.CONFLICT, "이미 처리된 장학금 신청입니다."),
    SCHOLARSHIP_ALREADY_APPLIED_THIS_SEMESTER(HttpStatus.CONFLICT, "이번 학기에 이미 장학금을 신청했습니다."),

    // 400 BAD_REQUEST
    ASSESSMENT_ID_REQUIRED(HttpStatus.BAD_REQUEST, "진단평가 ID가 필요합니다."),
    INVALID_OPTION_COUNT(HttpStatus.BAD_REQUEST, "옵션 개수는 1 이상이어야 합니다."),

    // == Extracurricular ==
    NOT_IN_APPLY_PERIOD(HttpStatus.BAD_REQUEST, "신청 기간이 아닙니다."),
    APPLICATION_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "모집 인원이 마감되었습니다."),
    ALREADY_PROCESSED_APPLICATION(HttpStatus.BAD_REQUEST, "이미 처리된 신청은 취소할 수 없습니다."),
    NON_EXISTENT_APPLICATION_INCLUDED(HttpStatus.BAD_REQUEST, "존재하지 않는 신청 내역이 포함되어 있습니다."),

    // == Consult ==
    INVALID_STATUS_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 상담 상태 코드입니다."),

    // == Mileage ==
    INSUFFICIENT_MILEAGE_SCORE(HttpStatus.BAD_REQUEST, "마일리지 점수가 부족합니다."),

    // 500 INTERNAL_SERVER_ERROR
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 발송에 실패했습니다."),
    TEMPORARY_PASSWORD_ISSUE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "임시 비밀번호 발급에 실패했습니다."),

    // == Diagnostic ==
    DIAGNOSTIC_TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "진단 검사를 찾을 수 없습니다."),
    DIAGNOSTIC_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "진단 문항을 찾을 수 없습니다."),
    DIAGNOSTIC_ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "진단 보기(답항)를 찾을 수 없습니다."),
    EXTERNAL_DIAGNOSIS_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 진단 API 호출 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}
