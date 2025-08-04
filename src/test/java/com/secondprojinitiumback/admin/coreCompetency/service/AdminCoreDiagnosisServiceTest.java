// src/test/java/com/secondprojinitiumback/admin/coreCompetency/service/AdminCoreDiagnosisServiceTest.java
package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AdminCoreDiagnosisService의 핵심역량진단 CRUD 기능 단위 테스트 클래스
 * 각 테스트는 정상/예외 동작을 검증한다.
 */
class AdminCoreDiagnosisServiceTest {

    // 진단, 코드, 학과 Repository를 Mock 객체로 선언
    @Mock
    private CoreCompetencyAssessmentRepository assessmentRepository;
    @Mock
    private CommonCodeRepository commonCodeRepository;
    @Mock
    private SchoolSubjectRepository schoolSubjectRepository;

    // Service에 Mock 객체 주입
    @InjectMocks
    private AdminCoreDiagnosisService service;

    /**
     * 각 테스트 실행 전 Mockito의 Mock 객체 초기화
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * [진단 등록] 성공 케이스
     * - 모든 코드 및 학과가 존재할 때 진단이 정상적으로 저장되는지 검증
     */
    @Test
    void createCoreCompetencyAssessment_success() {
        // 테스트용 DTO, 코드, 학과 엔티티 생성
        CoreCompetencyAssessmentDto dto = new CoreCompetencyAssessmentDto();
        dto.setSemesterCode("2");
        dto.setOnlineYn("Y");
        dto.setDepartmentName("ENG");
        dto.setAssessmentNo("1");
        dto.setAssessmentName("진단명");
        dto.setStartDate("2025-08-01");
        dto.setEndDate("2025-08-31");
        dto.setRegisterDate("2025-07-01");
        dto.setAcademicYear("2025");
        dto.setGuideContent("진단 안내");
        dto.setSemesterGroup("SEMESTER");
        dto.setOnlineExecGroup("ONLINE_YN");


        CommonCode semesterCode = mock(CommonCode.class);
        CommonCode onlineCode = mock(CommonCode.class);
        SchoolSubject subject = mock(SchoolSubject.class);

        // 코드 및 학과 조회 Mock 설정
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("2", "SEMESTER")).thenReturn(Optional.of(semesterCode));
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("Y", "ONLINE_YN")).thenReturn(Optional.of(onlineCode));
        when(schoolSubjectRepository.findBySubjectName("ENG")).thenReturn(Optional.of(subject));
        // 진단 저장 Mock 설정
        when(assessmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // 서비스 호출 및 결과 검증
        CoreCompetencyAssessment result = service.createCoreCompetencyAssessment(dto);

        assertThat(result.getAssessmentNo()).isEqualTo("1");
        assertThat(result.getAssessmentName()).isEqualTo("진단명");
        assertThat(result.getSemesterGroup()).isEqualTo("SEMESTER");
        assertThat(result.getOnlineExecGroupCode()).isEqualTo("ONLINE_YN");

        verify(assessmentRepository).save(any());
    }

    /**
     * [진단 등록] 코드 또는 학과가 없을 때 예외 발생 케이스
     */
    @Test
    void createCoreCompetencyAssessment_codeOrSubjectNotFound() {
        CoreCompetencyAssessmentDto dto = new CoreCompetencyAssessmentDto();
        dto.setSemesterCode("X");
        dto.setOnlineYn("Y");
        dto.setDepartmentName("ENG");

        // 학기 코드 조회 실패 Mock 설정
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("X", "SEMESTER")).thenReturn(Optional.empty());

        // 예외 발생 검증
        assertThrows(IllegalArgumentException.class, () -> service.createCoreCompetencyAssessment(dto));
    }

    /**
     * [진단 수정] 성공 케이스
     * - 기존 진단이 존재할 때 필드가 정상적으로 변경되는지 검증
     */
    @Test
    void updateCoreCompetencyAssessment_success() {
        CoreCompetencyAssessmentDto dto = new CoreCompetencyAssessmentDto();
        dto.setSemesterCode("2");
        dto.setOnlineYn("Y");
        dto.setDepartmentName("ENG");
        dto.setAssessmentNo("2");
        dto.setAssessmentName("수정명");
        dto.setStartDate("2025-09-01");
        dto.setEndDate("2025-09-30");
        dto.setRegisterDate("2025-08-01");
        dto.setAcademicYear("2025");
        dto.setGuideContent("수정 안내");
        dto.setSemesterGroup("SEMESTER");
        dto.setOnlineExecGroup("ONLINE_YN");


        CommonCode semesterCode = mock(CommonCode.class);
        CommonCode onlineCode = mock(CommonCode.class);
        SchoolSubject subject = mock(SchoolSubject.class);

        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .assessmentNo("1")
                .assessmentName("진단명")
                .build();

        // 코드, 학과, 진단 조회 Mock 설정
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("2", "SEMESTER")).thenReturn(Optional.of(semesterCode));
        when(commonCodeRepository.findById_CodeAndId_CodeGroup(("Y"), "ONLINE_YN")).thenReturn(Optional.of(onlineCode));
        when(schoolSubjectRepository.findBySubjectName("ENG")).thenReturn(Optional.of(subject));
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(assessment));
        when(assessmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // 서비스 호출 및 결과 검증
        CoreCompetencyAssessment result = service.updateCoreCompetencyAssessment(1L, dto);

        assertThat(result.getAssessmentNo()).isEqualTo("2");
        assertThat(result.getAssessmentName()).isEqualTo("수정명");
        assertThat(result.getSemesterGroup()).isEqualTo("SEMESTER");
        assertThat(result.getOnlineExecGroupCode()).isEqualTo("ONLINE_YN");

        verify(assessmentRepository).save(any());
    }

    /**
     * [진단 삭제] 성공 케이스
     * - 진단이 존재할 때 정상적으로 삭제되는지 검증
     */
    @Test
    void deleteCoreCompetencyAssessment_success() {
        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder().assessmentNo("1").build();
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(assessment));

        service.deleteCoreCompetencyAssessment(1L);

        verify(assessmentRepository).delete(assessment);
    }

    /**
     * [진단 상세 조회] 실패 케이스
     * - 진단이 존재하지 않을 때 예외 발생 검증
     */
    @Test
    void getCoreCompetencyAssessment_notFound() {
        when(assessmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getCoreCompetencyAssessment(99L));
    }

    /**
     * [진단 전체 조회] 빈 리스트 케이스
     * - 저장된 진단이 없을 때 빈 리스트 반환 검증
     */
    @Test
    void getAllCoreCompetencyAssessments_empty() {
        when(assessmentRepository.findAll()).thenReturn(Collections.emptyList());

        List<CoreCompetencyAssessment> result = service.getAllCoreCompetencyAssessments();

        assertThat(result).isEmpty();
        verify(assessmentRepository).findAll();
    }
}