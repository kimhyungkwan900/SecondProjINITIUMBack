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
 * AdminCoreDiagnosisService의 핵심역량 진단 CRUD 기능 단위 테스트 클래스
 * - 진단 등록, 수정, 삭제, 조회 기능이 올바르게 동작하는지 검증
 * - 공통코드 및 학과 코드가 존재하지 않는 경우 예외 발생도 함께 테스트
 */
class AdminCoreAssessmentServiceTest {

    // 진단, 코드, 학과 Repository를 Mock 객체로 선언
    @Mock
    private CoreCompetencyAssessmentRepository assessmentRepository;

    @Mock
    private CommonCodeRepository commonCodeRepository;

    @Mock
    private SchoolSubjectRepository schoolSubjectRepository;

    // 테스트 대상 서비스에 Mock 객체 주입
    @InjectMocks
    private AdminCoreAssessmentService service;

    /**
     * 각 테스트 실행 전 Mockito의 Mock 객체 초기화
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * [진단 등록 성공 테스트]
     * - 주어진 학기 코드, 온라인 여부 코드, 학과명이 모두 존재할 때 진단 등록이 성공하는지 검증
     */
    @Test
    void createCoreCompetencyAssessment_success() {
        // Given
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

        // 학기 코드, 온라인 여부 코드, 학과 조회 결과 설정
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("2", "SEMESTER"))
                .thenReturn(Optional.of(mock(CommonCode.class)));
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("Y", "ONLINE_YN"))
                .thenReturn(Optional.of(mock(CommonCode.class)));
        when(schoolSubjectRepository.findBySubjectName("ENG"))
                .thenReturn(Optional.of(mock(SchoolSubject.class)));

        // 진단 저장 동작 설정
        when(assessmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        CoreCompetencyAssessment result = service.createCoreCompetencyAssessment(dto);

        // Then
        assertThat(result.getAssessmentNo()).isEqualTo("1");
        assertThat(result.getAssessmentName()).isEqualTo("진단명");
        assertThat(result.getSemesterGroup()).isEqualTo("SEMESTER");
        assertThat(result.getOnlineExecGroupCode()).isEqualTo("ONLINE_YN");
        verify(assessmentRepository).save(any());
    }

    /**
     * [진단 등록 실패 테스트]
     * - 주어진 학기 코드가 존재하지 않을 경우 IllegalArgumentException 발생
     */
    @Test
    void createCoreCompetencyAssessment_codeOrSubjectNotFound() {
        // Given
        CoreCompetencyAssessmentDto dto = new CoreCompetencyAssessmentDto();
        dto.setSemesterCode("X");
        dto.setOnlineYn("Y");
        dto.setDepartmentName("ENG");

        // 학기 코드 조회 실패 설정
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("X", "SEMESTER"))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(IllegalArgumentException.class, () ->
                service.createCoreCompetencyAssessment(dto));
    }

    /**
     * [진단 수정 성공 테스트]
     * - 기존 진단이 존재할 때, 새로운 값으로 정상적으로 수정되는지 검증
     */
    @Test
    void updateCoreCompetencyAssessment_success() {
        // Given
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

        // 기존 진단 및 코드, 학과 존재 설정
        CoreCompetencyAssessment existing = CoreCompetencyAssessment.builder()
                .assessmentNo("1")
                .assessmentName("진단명")
                .build();

        when(commonCodeRepository.findById_CodeAndId_CodeGroup("2", "SEMESTER"))
                .thenReturn(Optional.of(mock(CommonCode.class)));
        when(commonCodeRepository.findById_CodeAndId_CodeGroup("Y", "ONLINE_YN"))
                .thenReturn(Optional.of(mock(CommonCode.class)));
        when(schoolSubjectRepository.findBySubjectName("ENG"))
                .thenReturn(Optional.of(mock(SchoolSubject.class)));
        when(assessmentRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(assessmentRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        CoreCompetencyAssessment result = service.updateCoreCompetencyAssessment(1L, dto);

        // Then
        assertThat(result.getAssessmentNo()).isEqualTo("2");
        assertThat(result.getAssessmentName()).isEqualTo("수정명");
        assertThat(result.getSemesterGroup()).isEqualTo("SEMESTER");
        assertThat(result.getOnlineExecGroupCode()).isEqualTo("ONLINE_YN");
        verify(assessmentRepository).save(any());
    }

    /**
     * [진단 삭제 성공 테스트]
     * - ID로 진단을 조회하고 정상적으로 삭제되는지 검증
     */
    @Test
    void deleteCoreCompetencyAssessment_success() {
        // Given
        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .assessmentNo("1")
                .build();
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(assessment));

        // When
        service.deleteCoreCompetencyAssessment(1L);

        // Then
        verify(assessmentRepository).delete(assessment);
    }

    /**
     * [진단 상세 조회 실패 테스트]
     * - 존재하지 않는 ID로 조회할 경우 예외가 발생하는지 검증
     */
    @Test
    void getCoreCompetencyAssessment_notFound() {
        // Given
        when(assessmentRepository.findById(99L)).thenReturn(Optional.empty());

        // Then
        assertThrows(IllegalArgumentException.class, () ->
                service.getCoreCompetencyAssessment(99L));
    }

    /**
     * [진단 전체 조회 빈 리스트 테스트]
     * - 저장된 진단이 없을 경우 빈 리스트가 반환되는지 검증
     */
    @Test
    void getAllCoreCompetencyAssessments_empty() {
        // Given
        when(assessmentRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<CoreCompetencyAssessment> result = service.getAllCoreCompetencyAssessments();

        // Then
        assertThat(result).isEmpty();
        verify(assessmentRepository).findAll();
    }
}
