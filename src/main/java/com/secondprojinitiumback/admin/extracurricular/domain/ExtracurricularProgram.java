package com.secondprojinitiumback.admin.extracurricular.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.domain.test.EmpInfo;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "extracurricular_program")
public class ExtracurricularProgram {

    @Id
    @Column(name = "edu_mng_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduMngId; // 비교과 프로그램 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", nullable = false)
    private EmpInfo empInfo; // 담당 교직원 (운영자)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctgrt_id")
    private ExtracurricularCategory extracurricularCategory; // 비교과 카테고리 (분류체계)

    @Column(name = "edu_nm")
    private String eduNm; // 프로그램 이름

    @Column(name = "edu_type")
    private EduType eduType; // 프로그램 유형 (TEAM/INDIVIDUAL)

    @Column(name = "edu_trgt_lmt")
    private EduTrgtLmt eduTrgtLmt; // 대상 제한 (학생/교직원/전체 등)

    @Column(name = "edu_gndr_lmt")
    private EduGndrLmt eduGndrLmt; // 성별 제한 (10=남자, 20=여자)

    @Column(name = "edu_slctn_type")
    private EduSlctnType eduSlctnType; // 선발 방식 (선착순 / 선발식 등)

    @Column(name = "edu_ptcp_nope")
    private int eduPtcpNope; // 모집 인원 수

    @Column(name = "edu_prps")
    private String eduPrps; // 프로그램 목적

    @Column(name = "edu_dtl_cn")
    private String eduDtlCn; // 프로그램 상세 설명

    @Column(name = "edu_aply_bgng_dt")
    private Date eduAplyBgngDt; // 신청 시작일

    @Column(name = "edu_aply_end_dt")
    private Date eduAplyEndDt; // 신청 마감일

    @Column(name = "edu_bgng_ymd")
    private Date eduBgngYmd; // 교육 시작일

    @Column(name = "edu_end_ymd")
    private Date eduEndYmd; // 교육 종료일

    @Column(name = "edu_plc_nm")
    private String eduPlcNm; // 교육 장소

    @Column(name = "edu_aply_dt")
    private Date eduAplyDt; // 프로그램 개설 신청일

    @Column(name = "stts_nm")
    private SttsNm sttsNm; // 프로그램 상태 (요청, 승인, 반려 등 ENUM)

    @Column(name = "edu_mlg")
    private int eduMlg; // 지급 마일리지

    @Column(name = "stts_shg_dt")
    private Date sttsShgDt; // 상태 변경일

    @Column(name = "field")
    private String field; // 예비 필드 (기타 정보 또는 내부 용도)

    @Column(name = "file_no")
    private String fileNo; // 첨부파일 참조 번호

}
