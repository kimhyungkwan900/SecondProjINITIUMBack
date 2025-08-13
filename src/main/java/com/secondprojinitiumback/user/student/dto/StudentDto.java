package com.secondprojinitiumback.user.student.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StudentDto {
    private String studentNo;            // 학번
    private String loginId;              // 로그인 ID
    private String universityCode;       // 대학 코드
    private String subjectCode;    // 학과 코드
    private String clubCode;             // 동아리 코드
    private String studentStatusCode;    // 학적 상태 코드
    private String bankAccountNo;        // 계좌번호
    private String name;                 // 이름
    private LocalDate admissionDate;     // 입학일자
    private LocalDate birthDate;         // 생년월일
    private String genderCode;           // 성별 코드
    private String email;                // 이메일
    private String empNo;            // 지도교수 ID
    private String grade;                // 학년

}
