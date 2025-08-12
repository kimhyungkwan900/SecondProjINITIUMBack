package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoDto {

    private String assessmentNo;
    private String studentNo;
    private String name;
    private String gender;
    private String subjectCode; //학과
    private String schoolYear;  //학년
    private String status;  //학적
    private String completeDate; //완료일(응답날짜로)
}
