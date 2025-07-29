package com.secondprojinitiumback.user.student.dto;

import com.secondprojinitiumback.common.constant.Gender;

import java.time.LocalDate;

public class addStudentDto {

    private String name;
    private String SchoolSubjectCode;
    private Gender gender;
    private String email;
    private String bankAccountNumber;
    private LocalDate birthDate;
    private LocalDate admissionDate;
}
