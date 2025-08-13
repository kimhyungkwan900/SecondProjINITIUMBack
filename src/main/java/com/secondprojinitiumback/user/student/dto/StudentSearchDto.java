package com.secondprojinitiumback.user.student.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentSearchDto {

    // 기본 정보 검색 필드
    private String studentNo;              // 학번(검색)
    private String name;                   // 이름(검색)
    private String universityCode;         // 대학 코드(검색)
    private String email;                  // 이메일(검색)

    // 학과 코드 (대분류/중분류)
    private String schoolSubjectCode;      // 학과 코드(검색) - 중분류
    private String schoolSubjectCodeSe;    // 학과 코드 그룹(검색) - 대분류 (기본값: CO0003)

    // 학적 상태 코드 (대분류/중분류)
    private String studentStatusCode;      // 학적 상태 코드(검색) - 중분류
    private String studentStatusCodeSe;    // 학적 상태 코드 그룹(검색) - 대분류 (기본값: SL0030)

    // 성별 코드 (대분류/중분류)
    private String genderCode;             // 성별 코드(검색) - 중분류
    private String genderCodeSe;           // 성별 코드 그룹(검색) - 대분류 (기본값: CO0001)

    // 기타 검색 조건
    private String grade;                  // 학년(검색) - 1~4학년
    private String advisorId;              // 지도교수 ID(검색) - Employee 테이블의 EMP_ID

    // 입학일자 기간 검색
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDateFrom;   // 입학일자 시작(검색)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDateTo;     // 입학일자 끝(검색)

    // 이메일 패턴 검증용 정규표현식
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    public String getSchoolSubjectCodeSe() {
        return StringUtils.hasText(schoolSubjectCodeSe) ? schoolSubjectCodeSe : "CO0003";
    }

    public String getStudentStatusCodeSe() {
        return StringUtils.hasText(studentStatusCodeSe) ? studentStatusCodeSe : "SL0030";
    }

    public String getGenderCodeSe() {
        return StringUtils.hasText(genderCodeSe) ? genderCodeSe : "CO0001";
    }


    // 입학일자 범위 유효성 검증
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
            int gradeNum = Integer.parseInt(grade.trim());
            return gradeNum >= 1 && gradeNum <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 이메일 형식 검증 (더 엄격한 검증)
    public boolean isValidEmail() {
        if (!StringUtils.hasText(email)) {
            return true; // null이나 빈 문자열은 검색조건 없음으로 간주
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    // 학번 형식 기본 검증 (숫자만 포함되어야 함)
    public boolean isValidStudentNo() {
        if (!StringUtils.hasText(studentNo)) {
            return true;
        }
        return studentNo.trim().matches("^[0-9]+$");
    }

    // 이름 형식 기본 검증 (특수문자 제외)
    public boolean isValidName() {
        if (!StringUtils.hasText(name)) {
            return true;
        }
        return name.trim().matches("^[가-힣a-zA-Z\\s]+$");
    }

    // 지도교수 ID 형식 검증 (교수는 P로 시작)
    public boolean isValidAdvisorId() {
        if (!StringUtils.hasText(advisorId)) {
            return true; // null이나 빈 문자열은 검색조건 없음으로 간주
        }
        // 교수 ID는 P로 시작해야 함
        return advisorId.trim().startsWith("P") && advisorId.trim().matches("^P[0-9]+$");
    }


    // 검색 조건이 하나라도 있는지 확인
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
        return isValidDateRange() &&
                isValidGrade() &&
                isValidEmail() &&
                isValidStudentNo() &&
                isValidName() &&
                isValidAdvisorId();
    }

    // 유효성 검증 실패 시 구체적인 오류 메시지 반환
    public String getValidationErrorMessage() {
        if (!isValidDateRange()) {
            return "입학일자 범위가 올바르지 않습니다. 시작일이 종료일보다 늦을 수 없습니다.";
        }
        if (!isValidGrade()) {
            return "학년은 1~4 사이의 숫자여야 합니다.";
        }
        if (!isValidEmail()) {
            return "올바른 이메일 형식이 아닙니다. (예: user@example.com)";
        }
        if (!isValidStudentNo()) {
            return "학번은 숫자만 입력 가능합니다.";
        }
        if (!isValidName()) {
            return "이름은 한글, 영문자만 입력 가능합니다.";
        }
        if (!isValidAdvisorId()) {
            return "지도교수 ID는 P로 시작하는 교수 번호여야 합니다.";
        }
        return null;
    }

    // 모든 문자열 필드의 공백 제거
    public void trimAllFields() {
        if (StringUtils.hasText(studentNo)) {
            this.studentNo = studentNo.trim();
        }
        if (StringUtils.hasText(name)) {
            this.name = name.trim();
        }
        if (StringUtils.hasText(email)) {
            this.email = email.trim();
        }
        if (StringUtils.hasText(grade)) {
            this.grade = grade.trim();
        }
    }

    // 검색 조건 초기화
    public void clearSearchConditions() {
        this.studentNo = null;
        this.name = null;
        this.universityCode = null;
        this.email = null;
        this.schoolSubjectCode = null;
        this.studentStatusCode = null;
        this.genderCode = null;
        this.grade = null;
        this.advisorId = null;
        this.admissionDateFrom = null;
        this.admissionDateTo = null;
    }

    // 설정된 검색 조건들을 문자열로 반환
    public String getActiveSearchConditions() {
        StringBuilder conditions = new StringBuilder();

        if (StringUtils.hasText(studentNo)) conditions.append("학번: ").append(studentNo).append(", ");
        if (StringUtils.hasText(name)) conditions.append("이름: ").append(name).append(", ");
        if (StringUtils.hasText(universityCode)) conditions.append("대학코드: ").append(universityCode).append(", ");
        if (StringUtils.hasText(schoolSubjectCode)) conditions.append("학과코드: ").append(schoolSubjectCode).append(", ");
        if (StringUtils.hasText(studentStatusCode)) conditions.append("학적상태: ").append(studentStatusCode).append(", ");
        if (StringUtils.hasText(grade)) conditions.append("학년: ").append(grade).append(", ");
        if (StringUtils.hasText(genderCode)) conditions.append("성별: ").append(genderCode).append(", ");
        if (StringUtils.hasText(advisorId)) conditions.append("지도교수: ").append(advisorId).append(", ");
        if (StringUtils.hasText(email)) conditions.append("이메일: ").append(email).append(", ");
        if (admissionDateFrom != null) conditions.append("입학일시작: ").append(admissionDateFrom).append(", ");
        if (admissionDateTo != null) conditions.append("입학일끝: ").append(admissionDateTo).append(", ");

        if (conditions.length() > 0) {
            conditions.setLength(conditions.length() - 2); // 마지막 ", " 제거
        }

        return conditions.toString();
    }
}