package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyAssessmentDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCoreCompetencyAssessmentService {

    // 핵심역량 진단 평가 레포지토리 (평가 목록 조회용)
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final CoreCompetencyResponseRepository coreCompetencyResponseRepository;

    /**
     * 전체 핵심역량 평가 목록을 조회하여 사용자용 DTO로 변환하여 반환
     *
     * @return UserCoreCompetencyAssessmentDTO 리스트
     */
    public List<UserCoreCompetencyAssessmentDTO> getAllAssessment() {
        // 1. DB에서 모든 평가(CoreCompetencyAssessment) 조회
        List<CoreCompetencyAssessment> assessments =
                coreCompetencyAssessmentRepository.findAll();

        // 2. 각 평가 엔티티를 사용자용 DTO로 변환
        return assessments.stream()
                .map(a -> UserCoreCompetencyAssessmentDTO.builder()
                        .id(a.getId())                         // 평가 ID
                        .assessmentNo(a.getAssessmentNo())      //진단명
                        .assessmentName(a.getAssessmentName()) // 평가명
                        .startDate(a.getStartDate())           // 시작일 (예: 20250801)
                        .endDate(a.getEndDate())               // 종료일 (예: 20250810)
                        .build()
                ).collect(Collectors.toList());
    }

    // ✅ 학생이 응답한 진단 “상세 목록” 바로 반환
    @Transactional
    public List<UserCoreCompetencyAssessmentDTO> getRespondedAssessment(String studentNo) {
        List<CoreCompetencyAssessment> assessments =
                coreCompetencyResponseRepository.findRespondedAssessments(studentNo);

        return assessments.stream()
                .map(a -> UserCoreCompetencyAssessmentDTO.builder()
                        .id(a.getId())
                        .assessmentNo(a.getAssessmentNo())      //진단명
                        .assessmentName(a.getAssessmentName())
                        .startDate(a.getStartDate())
                        .endDate(a.getEndDate())
                        .build())
                .toList();
    }

    // 로그인 사용자(학생)가 응답한 진단 ID 목록
    @Transactional
    public boolean hasStudentResponded(Long assessmentId, String studentNo) {
        return coreCompetencyResponseRepository.existsByAssessment_IdAndStudent_StudentNo(assessmentId, studentNo);
    }

    // 특정 진단에 대해 내가 응답했는지 여부
    @Transactional
    public List<Long> getRespondedAssessmentIds(String studentNo) {
        return coreCompetencyResponseRepository.findDistinctAssessmentIdsByStudentNo(studentNo);
    }
}
