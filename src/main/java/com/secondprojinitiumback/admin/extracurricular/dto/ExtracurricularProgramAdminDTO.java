package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    private int request;
    private int accept; // 참여 인원 반환
    private int totalSurvey; // 등록된 설문 개수

    private String empNo;
    private String name;

    private String subjectName;

    private List<ExtracurricularApplyDTO> applyList;

    private List<ExtracurricularScheduleDTO> scheduleList;
}
