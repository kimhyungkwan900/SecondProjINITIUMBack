package com.secondprojinitiumback.user.student.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class EnrollStudentDto {

    private String name;                // 이름
    private String schoolSubjectCode;   // 학과 코드
    private String gender;              // 성별 코드
    private String email;               // 이메일
    private String bankAccountNo;       // 계좌번호
    private String grade;               // 학년
    private String advisorNo;           // 지도교수 ID
    private LocalDate birthDate;        // 생년월일
    private LocalDate admissionDate;    // 입학일자
    private String universityCode;      // 대학 코드
    private String studentStatusCode;   // 학적 상태 코드
    private String clubCode;            // 동아리 코드
}
