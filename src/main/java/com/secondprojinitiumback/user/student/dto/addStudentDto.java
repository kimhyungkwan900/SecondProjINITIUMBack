package com.secondprojinitiumback.user.student.dto;

import com.secondprojinitiumback.user.employee.domain.Employee;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class addStudentDto {

    private String name;
    private String SchoolSubjectCode;
    private String gender;
    private String email;
    private String bankAccountNumber;
    private String grade;
    private String advisorNo;
    private LocalDate birthDate;
    private LocalDate admissionDate;

}
