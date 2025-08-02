package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduGndrLmt;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduSlctnType;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduTrgtLmt;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduType;
import com.secondprojinitiumback.admin.extracurricular.domain.test.EmpInfo;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularProgramDTO {
    private Long eduMngId;
    private EmpInfo empInfo;
    private ExtracurricularCategoryDTO extracurricularCategoryDTO;
    private String eduNm;
    private EduType eduType;
    private EduTrgtLmt eduTrgtLmt;
    private EduGndrLmt eduGndrLmt;
    private EduSlctnType eduSlctnType;


}
