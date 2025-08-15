package com.secondprojinitiumback.common.security.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {
    private String loginId;
    private String userType;
    private String name;
    private String email;
    private String studentNo;
    private String empNo;
    private String schoolSubject;
    private String gender;
    private String grade;
    private Boolean passwordChangeRequired;
    private Integer loginFailCount;
}
