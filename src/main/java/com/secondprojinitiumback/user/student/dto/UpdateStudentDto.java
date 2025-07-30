package com.secondprojinitiumback.user.student.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UpdateStudentDto {
    private String email;           // 이메일
    private String bankAccountNo;   // 계좌번호

}
