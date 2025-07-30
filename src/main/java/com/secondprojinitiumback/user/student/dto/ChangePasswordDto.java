package com.secondprojinitiumback.user.student.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ChangePasswordDto {
    private String currentPassword; // 기존 비밀번호
    private String newPassword;     // 새 비밀번호
}
