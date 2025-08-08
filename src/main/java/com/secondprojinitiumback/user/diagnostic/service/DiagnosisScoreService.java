package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import com.secondprojinitiumback.user.diagnostic.repository.DiagnosticScoreLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiagnosisScoreService {

    private final DiagnosticScoreLevelRepository levelRepository;// 점수 해석 구간 조회용 Repository

    /**
     * 총점 계산
     * - 결과 상세 리스트에서 score 값을 합산
     */
    public int calculateTotalScore(List<DiagnosticResultDetail> details) {
        return details.stream()
                .mapToInt(DiagnosticResultDetail::getScore)
                .sum();
    }

    /**
     * 점수 해석
     * - 특정 testId와 점수에 해당하는 해석 구간(DiagnosticScoreLevel) 조회
     * - minScore ≤ score ≤ maxScore 범위 검색
     * - 구간이 존재하면 "레벨명 - 설명" 형식 반환, 없으면 기본 메시지 반환
     */
    public String interpretScore(Long testId, int score) {
        return levelRepository.findByTestIdAndMinScoreLessThanEqualAndMaxScoreGreaterThanEqual(testId, score, score)
                .map(level -> level.getLevelName() + " - " + level.getDescription())
                .orElse("해석 불가 점수");
    }
}