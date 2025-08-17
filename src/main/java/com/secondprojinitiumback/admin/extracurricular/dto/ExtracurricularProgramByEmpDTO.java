package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExtracurricularProgramByEmpDTO {
    private Long eduMngId;          // 비교과 프로그램 ID
    private String eduNm;           // 프로그램 이름
    private EduType eduType;        // 프로그램 유형 (TEAM/INDIVIDUAL)
    private EduTrgtLmt eduTrgtLmt;  // 대상 제한 (학생/교직원/전체 등)
    private EduGndrLmt eduGndrLmt;  // 성별 제한 (10=남자, 20=여자)
    private EduSlctnType eduSlctnType;  // 선발 방식 (선착순 / 선발식 등)
    private int eduPtcpNope;        // 모집 인원 수
    private String eduPrps;         // 프로그램 목적
    private String eduDtlCn;        // 프로그램 상세 설명
    private LocalDateTime eduAplyBgngDt;    // 신청 시작일
    private LocalDateTime eduAplyEndDt;     // 신청 종료일
    private LocalDate eduBgngYmd;// 프로그램 시작일
    private LocalDate eduEndYmd;// 프로그램 종료일
    private String eduPlcNm;// 프로그램 장소
    private LocalDateTime eduAplyDt;// 프로그램 신청일
    private SttsNm sttsNm;// 프로그램 상태 (예: 진행중, 종료 등)
    private int eduMlg;// 프로그램 마일리지
    private LocalDateTime sttsChgDt;// 상태 변경일
    private String cndCn;// 수료 조건
    
    // 카테고리 정보
    private String ctgryNm;
    
    // 신청/참여 현황
    private int request;  // 신청 인원
    private int accept;   // 참여 인원
}