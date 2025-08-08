package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticScoreLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosticScoreLevelRepository extends JpaRepository<DiagnosticScoreLevel, Long> {
    /**
     * 특정 검사(testId)에서 점수(score)가 속하는 구간의 레벨 조회
     * - minScore ≤ score ≤ maxScore 조건에 해당하는 레벨 반환
     * - 반환 타입 Optional: 해당 구간이 없을 수도 있음
     * - score1, score2: 같은 점수를 두 번 전달하여 min/max 비교 양쪽에 사용
     */
    Optional<DiagnosticScoreLevel> findByTestIdAndMinScoreLessThanEqualAndMaxScoreGreaterThanEqual(
            Long testId, Integer score1, Integer score2
    ); // 해당 점수 구간에 해당하는 레벨 조회
}