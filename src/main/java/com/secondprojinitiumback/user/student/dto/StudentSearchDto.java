package com.secondprojinitiumback.user.student.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StudentSearchDto {
    private String studentNo;              // 학번(검색)
    private String name;                   // 이름(검색)
    private String universityCode;         // 대학 코드(검색)
    private String schoolSubjectCode;      // 학과 코드(검색)
    private String schoolSubjectCodeSe;      // 학과 코드(검색)
    private String clubCode;               // 동아리 코드(검색)
    private String studentStatusCode;      // 학적 상태 코드(검색)
    private String studentStatusCodeSe;      // 학적 상태 코드(검색)
    private String grade;                  // 학년(검색)
    private String genderCode;             // 성별 코드(검색)
    private String genderCodeSe;             // 성별 코드(검색)
    private String advisorId;              // 지도교수 ID(검색)
    private String email;                  // 이메일(검색)
    private LocalDate admissionDateFrom;   // 입학일자 시작(검색)
    private LocalDate admissionDateTo;     // 입학일자 끝(검색)
}
