package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularImageDTO;
import com.secondprojinitiumback.user.employee.domain.Employee;
import jakarta.persistence.*;
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

    private String eduNm; // 프로그램 이름
    private EduType eduType; // 프로그램 유형 (TEAM/INDIVIDUAL)
    private EduTrgtLmt eduTrgtLmt; // 대상 제한 (학생/교직원/전체 등)
    private EduGndrLmt eduGndrLmt; // 성별 제한 (10=남자, 20=여자)
    private EduSlctnType eduSlctnType; // 선발 방식 (선착순 / 선발식 등)
    private int eduPtcpNope; // 모집 인원 수
    private String eduPrps; // 프로그램 목적
    private String eduDtlCn; // 프로그램 상세 설명
    private LocalDateTime eduAplyBgngDt; // 신청 시작일
    private LocalDateTime eduAplyEndDt; // 신청 마감일
    private LocalDate eduBgngYmd; // 교육 시작일
    private LocalDate eduEndYmd; // 교육 종료일
    private String eduPlcNm; // 교육 장소
    private LocalDateTime eduAplyDt; // 프로그램 개설 신청일
    private SttsNm sttsNm; // 프로그램 상태 (요청, 승인, 반려 등 ENUM)
    private int eduMlg; // 지급 마일리지
    private LocalDateTime sttsChgDt; // 상태 변경일
    private String cndCn; //  수료 조건
    private String field; // 예비 필드 (기타 정보 또는 내부 용도)
    private String fileNo; // 첨부파일 참조 번호

    private int accept; // 참여 인원 반환
    private List<ExtracurricularImageDTO> extracurricularImageDTO; //  이미지 반환

    private String ctgryNm;

}
