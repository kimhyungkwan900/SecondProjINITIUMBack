package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.domain.test.EmpInfo;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularProgramFormDTO {

    private Long eduMngId; // 비교과 프로그램 ID (PK)
    private EmpInfo empInfo; // 담당 교직원 (운영자)
    private ExtracurricularCategory extracurricularCategory; // 비교과 카테고리 (분류체계)
    private String eduNm; // 프로그램 이름
    private EduType eduType; // 프로그램 유형 (TEAM/INDIVIDUAL)
    private EduTrgtLmt eduTrgtLmt; // 대상 제한 (학생/교직원/전체 등)
    private EduGndrLmt eduGndrLmt; // 성별 제한 (10=남자, 20=여자)
    private EduSlctnType eduSlctnType; // 선발 방식 (선착순 / 선발식 등)
    private int eduPtcpNope; // 모집 인원 수
    private String eduPrps; // 프로그램 목적
    private String eduDtlCn; // 프로그램 상세 설명
    private Date eduAplyBgngDt; // 신청 시작일
    private Date eduAplyEndDt; // 신청 마감일
    private Date eduBgngYmd; // 교육 시작일
    private Date eduEndYmd; // 교육 종료일
    private String eduPlcNm; // 교육 장소
    private Date eduAplyDt; // 프로그램 개설 신청일
    private SttsNm sttsNm; // 프로그램 상태 (요청, 승인, 반려 등 ENUM)


}
