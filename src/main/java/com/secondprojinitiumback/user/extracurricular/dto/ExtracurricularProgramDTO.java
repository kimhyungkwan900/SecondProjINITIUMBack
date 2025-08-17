package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.dto.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularProgramDTO {
    private Long eduMngId;
    private Long ctgryId;
    private String empNo;

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
    private LocalDate eduEndYmd;
    private String eduPlcNm;
    private LocalDateTime eduAplyDt;
    private SttsNm sttsNm;
    private int eduMlg;
    private LocalDateTime sttsChgDt;
    private String cndCn;
    private String field;
    private String fileNo;

    private int accept;
    private List<ExtracurricularImageDTO> extracurricularImageDTO;

    private String ctgryNm;

    private String name;
    private String email;
    private String subjectName;
    private String tel;

    private List<ExtracurricularScheduleDTO> extracurricularSchedules;

    // === 프론트 호환용 별칭 ===
    @com.fasterxml.jackson.annotation.JsonProperty("categoryName")
    public String getCategoryName() {
        return this.ctgryNm;
    }
}

