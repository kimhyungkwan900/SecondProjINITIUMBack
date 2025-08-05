package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExtracurricularProgramAdminDTO {
    private Long eduMngId;
    private ExtracurricularCategoryDTO extracurricularCategoryDTO;
    private String eduNm;
    private EduType eduType;
    private EduTrgtLmt eduTrgtLmt;
    private EduGndrLmt eduGndrLmt;
    private EduSlctnType eduSlctnType;
    private int eduPtcpNope;
    private String eduPrps;
    private String eduDtlCn;
    private LocalDateTime eduAplyBgngDt;
    private LocalDateTime eduAplyEndDt;
    private LocalDate eduBgngYmd;
    private LocalDate eduEndDt;
    private String eduPlcNm;
    private LocalDateTime eduAplyDt;
    private SttsNm eduSttsNm;

    private ExtracurricularImageDTO extracurricularImageDTO;

    private String empNo;
    private String name;

    private String subjectName;
}
