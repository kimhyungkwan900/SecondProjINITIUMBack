package com.secondprojinitiumback.admin.extracurricular.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolSubjectDTO {
    private String subjectCode;
    private String subjectName;

    public SchoolSubjectDTO(String subjectCode, String subjectName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }
}
