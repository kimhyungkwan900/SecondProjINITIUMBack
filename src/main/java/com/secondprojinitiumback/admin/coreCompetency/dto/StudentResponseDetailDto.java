package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDetailDto {

    private String assessmentNo;
    private String studentNo;
    private Integer questionNo;
    private String label;
}
