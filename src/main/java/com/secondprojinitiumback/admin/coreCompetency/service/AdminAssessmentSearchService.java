package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 📘 AdminAssessmentSearchService
 * - 관리자 페이지에서 핵심역량 진단 평가 목록을 조건에 따라 조회하는 서비스
 */
@Service
@RequiredArgsConstructor
public class AdminAssessmentSearchService {

    // 핵심역량 진단 평가 레포지토리 의존성 주입
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;

    /**
     * ✅ 진단목록 조회 (상세 정보 포함)
     *
     * @param year        학년도 (예: "2025")
     * @param semester    학기명 (예: "1학기", "2학기")
     * @param assessmentNo 진단번호 (예: "ASMT2025-01")
     * @return CoreCompetencyAssessmentDto 리스트 (상세 정보 포함)
     *
     * - AdminAssessmentSearchBar + AdminAssessmentListTable에서 사용
     * - semesterCode와 onlineYn은 CodeName 기준으로 조회됨
     */
    public List<CoreCompetencyAssessmentDto> assessmentList(String year, String semester, String assessmentNo) {
        List<CoreCompetencyAssessment> assessments =
                coreCompetencyAssessmentRepository.findByConditions(year, semester, assessmentNo);

        // Entity → DTO 변환 (상세용 fromEntity 사용)
        return assessments.stream()
                .map(CoreCompetencyAssessmentDto::fromEntity)
                .toList();
    }

    /**
     * ✅ 결과표 전용 진단 목록 조회 (차트/분석 기준만 포함)
     *
     * @param year        학년도
     * @param semester    학기명
     * @param assessmentNo 진단번호
     * @return CoreCompetencyAssessmentDto 리스트 (간략 정보만 포함)
     *
     * - AdminAssessmentResultListTable 등에서 결과표 표시용으로 사용
     */
    public List<CoreCompetencyAssessmentDto> assessmentListResult(String year, String semester, String assessmentNo) {
        List<CoreCompetencyAssessment> assessments =
                coreCompetencyAssessmentRepository.findByConditions(year, semester, assessmentNo);

        // Entity → DTO 변환 (차트/분석 기준용 fromEntity2 사용)
        return assessments.stream()
                .map(CoreCompetencyAssessmentDto::fromEntity2)
                .toList();
    }
}
