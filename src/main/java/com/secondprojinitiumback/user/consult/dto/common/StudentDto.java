package com.secondprojinitiumback.user.consult.dto.common;

import com.secondprojinitiumback.common.domain.SchoolSubject;
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

    private SchoolSubject schoolSubject;

    private String name;
}
