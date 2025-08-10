package com.secondprojinitiumback.user.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UpdateStudentDto {
    @Email
    private String email;           // 이메일

    @Pattern(regexp="\\d{6,}")
    private String bankAccountNo;   // 계좌번호

    private String bankCd;          // 은행 코드
}
