package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCoreDiagnosisService {

    private final CommonCodeRepository commonCodeRepository;
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final SchoolSubjectRepository schoolSubjectRepository;

    // 1. 핵심 역량 진단 등록
    public CoreCompetencyAssessment createCoreCompetencyAssessment(CoreCompetencyAssessmentDto assessmentDto) {
        CommonCode semesterCode = commonCodeRepository.findByCodeAndGroup(assessmentDto.getSemesterCode(), "SEMESTER")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학기 코드입니다."));

        CommonCode onlineExecCode = commonCodeRepository.findByCodeAndGroup(assessmentDto.getOnlineYn(), "ONLINE_YN")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 온라인 여부 코드입니다."));

        SchoolSubject schoolSubject = schoolSubjectRepository.findByDeptDivisionCode(assessmentDto.getDepartmentName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서 코드입니다."));

        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .assessmentNo(assessmentDto.getAssessmentNo())
                .assessmentName(assessmentDto.getAssessmentName())
                .startDate(assessmentDto.getStartDate())
                .endDate(assessmentDto.getEndDate())
                .registerDate(assessmentDto.getRegisterDate())
                .academicYear(assessmentDto.getAcademicYear())
                .semesterCode(semesterCode)
                .semesterGroup(assessmentDto.getSemesterGroup()) // 중요
                .onlineExecCode(onlineExecCode)
                .onlineExecGroupCode(assessmentDto.getOnlineExecGroup()) // 중요
                .guideContent(assessmentDto.getGuideContent())
                .schoolSubject(schoolSubject)
                .build();

        return coreCompetencyAssessmentRepository.save(assessment);
    }


    // 2. 핵심 역량 진단 수정
    public CoreCompetencyAssessment updateCoreCompetencyAssessment(Long assessmentId, CoreCompetencyAssessmentDto assessmentDto) {

        CommonCode semesterCode = commonCodeRepository.findByCodeAndGroup(assessmentDto.getSemesterCode(), "SEMESTER")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학기 코드입니다."));

        CommonCode onlineExecCode = commonCodeRepository.findByCodeAndGroup(assessmentDto.getOnlineYn(), "ONLINE_YN")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 온라인 여부 코드입니다."));

        SchoolSubject schoolSubject = schoolSubjectRepository.findByDeptDivisionCode(assessmentDto.getDepartmentName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서 코드입니다."));

        CoreCompetencyAssessment existingAssessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));

        existingAssessment.setAssessmentNo(assessmentDto.getAssessmentNo());
        existingAssessment.setAssessmentName(assessmentDto.getAssessmentName());
        existingAssessment.setStartDate(assessmentDto.getStartDate());
        existingAssessment.setEndDate(assessmentDto.getEndDate());
        existingAssessment.setRegisterDate(assessmentDto.getRegisterDate());
        existingAssessment.setAcademicYear(assessmentDto.getAcademicYear());
        existingAssessment.setSemesterCode(semesterCode);
        existingAssessment.setSemesterGroup(assessmentDto.getSemesterGroup());
        existingAssessment.setOnlineExecCode(onlineExecCode);
        existingAssessment.setOnlineExecGroupCode(assessmentDto.getOnlineExecGroup());
        existingAssessment.setGuideContent(assessmentDto.getGuideContent());
        existingAssessment.setSchoolSubject(schoolSubject);

        return coreCompetencyAssessmentRepository.save(existingAssessment);
    }


    // 3. 핵심 역량 진단 삭제
    public void deleteCoreCompetencyAssessment(Long assessmentId) {
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));
        coreCompetencyAssessmentRepository.delete(assessment);
    }

    // 4. 핵심 역량 진단 조회
    public CoreCompetencyAssessment getCoreCompetencyAssessment(Long assessmentId) {
        return coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));
    }

    // 5. 핵심 역량 진단 목록 전체 조회
    public List<CoreCompetencyAssessment> getAllCoreCompetencyAssessments() {
        return coreCompetencyAssessmentRepository.findAll();
    }

    // 페이징 처리 (미구현)
}
