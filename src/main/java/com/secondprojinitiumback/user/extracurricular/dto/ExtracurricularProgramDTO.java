package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduGndrLmt;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduSlctnType;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduTrgtLmt;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduType;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryDTO;
import com.secondprojinitiumback.user.employee.domain.Employee;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularProgramDTO {
    private Long eduMngId;
    private Employee employee;
    private ExtracurricularCategoryDTO extracurricularCategoryDTO;
    private String eduNm;
    private EduType eduType;
    private EduTrgtLmt eduTrgtLmt;
    private EduGndrLmt eduGndrLmt;
    private EduSlctnType eduSlctnType;


}
