package com.secondprojinitiumback.user.student.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AdminUpdateStudentDto {
    private String email;               // 이메일
    private String clubCode;            // 동아리 코드
    private String grade;               // 학년
    private String bankAccountNo;       // 계좌번호
    private String advisorNo;           // 지도교수 ID
    private String schoolSubjectCode;   // 학과 코드
    private String studentStatusCode;   // 학적 상태 코드
    private String admissionDate;       // 입학일자
}
