package com.secondprojinitiumback.user.consult.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {

    private String studentNo;

    private String schoolSubject;

    private String name;

    private String email;
}
