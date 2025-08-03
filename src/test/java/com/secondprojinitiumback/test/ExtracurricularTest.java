package com.secondprojinitiumback.test;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.domain.test.EmpInfoRepository;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramFormDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramUpdateFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularCategoryRepository;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularProgramService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class ExtracurricularTest {

    @Autowired
    private ExtracurricularProgramService extracurricularProgramService;

    @Autowired
    private EmpInfoRepository empInfoRepository;

    @Autowired
    private ExtracurricularCategoryRepository extracurricularCategoryRepository;

    @Test
    @DisplayName("프로그램 등록 테스트")
    public void testInsertExtracurricularProgram() {
        String empId = "E000000001"; // 예시 사원 ID

        ExtracurricularProgramFormDTO dto = ExtracurricularProgramFormDTO.builder()
                .extracurricularCategory(extracurricularCategoryRepository.findById(1L)
                        .orElseThrow(() -> new RuntimeException("카테고리 없음")))
                .eduNm("자기계발 프로그램")
                .eduType(EduType.TEAM)
                .eduTrgtLmt(EduTrgtLmt.ALL)
                .eduGndrLmt(EduGndrLmt.MALE)
                .eduSlctnType(EduSlctnType.FIRSTCOME)
                .eduPtcpNope(30)
                .eduPrps("학생 자율 역량 강화")
                .eduDtlCn("자기계발 및 역량 기반 교육")
                .eduAplyBgngDt(LocalDateTime.now().minusDays(1))
                .eduAplyEndDt(LocalDateTime.now().plusDays(5))
                .eduBgngYmd(LocalDate.now().plusDays(6))
                .eduEndYmd(LocalDate.now().plusDays(10))
                .eduPlcNm("복지관 3층")
                // 서비스 내부에서 현재시간 설정하므로 여기선 안 넣어도 됨
                // .eduAplyDt(LocalDateTime.now())
                .sttsNm(SttsNm.REQUESTED)
                .eduDays(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)) // 반복 요일 추가
                .eduStartTime(LocalTime.of(14, 0))
                .eduEndTime(LocalTime.of(16, 0))
                .build();

        extracurricularProgramService.insertExtracurricularProgram(dto, empId);
    }

    @Test
    @DisplayName("비교과 프로그램 승인 처리 테스트")
    public void testUpdateProgramStatus() {
        Long eduMngId = 10L; // 테스트 대상 비교과 프로그램 ID (실제 DB에 있어야 함)

        ExtracurricularProgramUpdateFormDTO dto = ExtracurricularProgramUpdateFormDTO.builder()
                .eduMngId(eduMngId)
                .sttsNm(SttsNm.APPROVED)  // 상태 변경 (예: APPROVED 또는 REJECTED)
                .eduMlg(3)                // 마일리지 변경
                .build();

        extracurricularProgramService.updateExtracurricularProgram(dto);
    }

}
