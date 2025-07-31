package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.entity.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCoreDiagnosisService {

    private final CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;
    private final SubCompetencyCategoryRepository SubCompetencyCategoryRepository;
    private final CommonCodeRepository CommonCodeRepository;
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;

    //1. 핵심 역량 진단 등록
    public CoreCompetencyAssessment createCoreCompetencyAssessment(CoreCompetencyAssessmentDto assessmentDto) {

        // 필수 필드 검증
        CoreCompetencyCategory coreCategory = coreCompetencyCategoryRepository.findById(assessmentDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("핵심 역량 카테고리가 존재하지 않습니다."));

        SubCompetencyCategory subCategory = SubCompetencyCategoryRepository.findById(assessmentDto.getSubCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("하위 역량 카테고리가 존재하지 않습니다."));

        CommonCode semesterCode = CommonCodeRepository.findByCodeAndGroup(assessmentDto.getSemesterCode(), "SEMESTER")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학기 코드입니다."));

        CommonCode onlineExecCode = CommonCodeRepository.findByCodeAndGroup(assessmentDto.getOnlineYn(), "ONLINE_YN")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 온라인 여부 코드입니다."));
        // DTO를 엔티티로 변환
        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .coreCategory(coreCategory)
                .subCategory(subCategory)
                .assessmentNo(assessmentDto.getAssessmentNo())
                .assessmentName(assessmentDto.getAssessmentName())
                .startDate(assessmentDto.getStartDate())
                .endDate(assessmentDto.getEndDate())
                .registerDate(assessmentDto.getRegisterDate())
                .academicYear(assessmentDto.getAcademicYear())
                .semesterCode(semesterCode)
                .onlineExecCode(onlineExecCode)
                .guideContent(assessmentDto.getGuideContent())
                .agreementContent(assessmentDto.getAgreementContent())
                .build();

        // 엔티티를 저장하고 반환
        return coreCompetencyAssessmentRepository.save(assessment);
    }

    //2. 핵심 역량 진단 수정
    public CoreCompetencyAssessment updateCoreCompetencyAssessment(Long assessmentId, CoreCompetencyAssessmentDto assessmentDto) {
        // 필수 필드 검증
        CoreCompetencyCategory coreCategory = coreCompetencyCategoryRepository.findById(assessmentDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("핵심 역량 카테고리가 존재하지 않습니다."));

        SubCompetencyCategory subCategory = SubCompetencyCategoryRepository.findById(assessmentDto.getSubCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("하위 역량 카테고리가 존재하지 않습니다."));

        CommonCode semesterCode = CommonCodeRepository.findByCodeAndGroup(assessmentDto.getSemesterCode(), "SEMESTER")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학기 코드입니다."));

        CommonCode onlineExecCode = CommonCodeRepository.findByCodeAndGroup(assessmentDto.getOnlineYn(), "ONLINE_YN")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 온라인 여부 코드입니다."));

        // 기존 엔티티 조회
        CoreCompetencyAssessment existingAssessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));

        // 엔티티 업데이트
        existingAssessment.setCoreCategory(coreCategory);
        existingAssessment.setSubCategory(subCategory);
        existingAssessment.setAssessmentNo(assessmentDto.getAssessmentNo());
        existingAssessment.setAssessmentName(assessmentDto.getAssessmentName());
        existingAssessment.setStartDate(assessmentDto.getStartDate());
        existingAssessment.setEndDate(assessmentDto.getEndDate());
        existingAssessment.setRegisterDate(assessmentDto.getRegisterDate());
        existingAssessment.setAcademicYear(assessmentDto.getAcademicYear());
        existingAssessment.setSemesterCode(semesterCode);
        existingAssessment.setOnlineExecCode(onlineExecCode);
        existingAssessment.setGuideContent(assessmentDto.getGuideContent());
        existingAssessment.setAgreementContent(assessmentDto.getAgreementContent());

        // 업데이트된 엔티티 저장
        return coreCompetencyAssessmentRepository.save(existingAssessment);

    }

    //3. 핵심 역량 진단 삭제
    public void deleteCoreCompetencyAssessment(Long assessmentId) {
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));
        coreCompetencyAssessmentRepository.delete(assessment);
    }

    //4. 핵심 역량 진단 조회
    public CoreCompetencyAssessment getCoreCompetencyAssessment(Long assessmentId) {
        return coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));
    }

    //5. 핵심 역량 진단 목록 전체 조회
    public List<CoreCompetencyAssessment> getAllCoreCompetencyAssessments() {
        return coreCompetencyAssessmentRepository.findAll();
    }

    //페이징처리
}