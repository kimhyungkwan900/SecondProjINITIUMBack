package com.secondprojinitiumback.user.student.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentSearchDto {
    private String studentNo;              // 학번(검색)
    private String name;                   // 이름(검색)
    private String universityCode;         // 대학 코드(검색)

    // 학과 코드 (대분류/중분류)
    private String schoolSubjectCode;      // 학과 코드(검색) - 중분류
    private String schoolSubjectCodeSe;    // 학과 코드 그룹(검색) - 대분류 (기본값: CO0003)

    // 학적 상태 코드 (대분류/중분류)
    private String studentStatusCode;      // 학적 상태 코드(검색) - 중분류
    private String studentStatusCodeSe;    // 학적 상태 코드 그룹(검색) - 대분류 (기본값: SL0030)

    private String grade;                  // 학년(검색)

    // 성별 코드 (대분류/중분류)
    private String genderCode;             // 성별 코드(검색) - 중분류
    private String genderCodeSe;           // 성별 코드 그룹(검색) - 대분류 (기본값: CO0001)

    private String advisorId;              // 지도교수 ID(검색)
    private String email;                  // 이메일(검색)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDateFrom;   // 입학일자 시작(검색)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDateTo;     // 입학일자 끝(검색)

    // Repository에서 사용할 수 있도록 기본값을 제공하는 getter 메서드들
    public String getSchoolSubjectCodeSe() {
        return StringUtils.hasText(schoolSubjectCodeSe) ? schoolSubjectCodeSe : "CO0003";
    }

    public String getStudentStatusCodeSe() {
        return StringUtils.hasText(studentStatusCodeSe) ? studentStatusCodeSe : "SL0030";
    }

    public String getGenderCodeSe() {
        return StringUtils.hasText(genderCodeSe) ? genderCodeSe : "CO0001";
    }

    // 유효성 검증 메서드
    public boolean isValidDateRange() {
        if (admissionDateFrom != null && admissionDateTo != null) {
            return !admissionDateFrom.isAfter(admissionDateTo);
        }
        return true;
    }

    // 학년 유효성 검증 (1-4학년)
    public boolean isValidGrade() {
        if (!StringUtils.hasText(grade)) {
            return true; // null이나 빈 문자열은 검색조건 없음으로 간주
        }
        try {
            int gradeNum = Integer.parseInt(grade);
            return gradeNum >= 1 && gradeNum <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 이메일 기본 형식 검증
    public boolean isValidEmail() {
        if (!StringUtils.hasText(email)) {
            return true; // null이나 빈 문자열은 검색조건 없음으로 간주
        }
        return email.contains("@");
    }

    // 검색 조건이 있는지 확인하는 메서드
    public boolean hasSearchCondition() {
        return StringUtils.hasText(studentNo) ||
                StringUtils.hasText(name) ||
                StringUtils.hasText(universityCode) ||
                StringUtils.hasText(schoolSubjectCode) ||
                StringUtils.hasText(studentStatusCode) ||
                StringUtils.hasText(grade) ||
                StringUtils.hasText(genderCode) ||
                StringUtils.hasText(advisorId) ||
                StringUtils.hasText(email) ||
                admissionDateFrom != null ||
                admissionDateTo != null;
    }

    // 전체 유효성 검증
    public boolean isValid() {
        return isValidDateRange() && isValidGrade() && isValidEmail();
    }

    // 유효성 검증 실패 시 오류 메시지 반환
    public String getValidationErrorMessage() {
        if (!isValidDateRange()) {
            return "입학일자 범위가 올바르지 않습니다. 시작일이 종료일보다 늦을 수 없습니다.";
        }
        if (!isValidGrade()) {
            return "학년은 1-4 사이의 숫자여야 합니다.";
        }
        if (!isValidEmail()) {
            return "이메일 형식이 올바르지 않습니다. '@'가 포함되어야 합니다.";
        }
        return null;
    }
}