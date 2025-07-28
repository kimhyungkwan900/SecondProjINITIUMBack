package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticScoreLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosticScoreLevelRepository extends JpaRepository<DiagnosticScoreLevel, Long> {
    Optional<DiagnosticScoreLevel> findByTestIdAndMinScoreLessThanEqualAndMaxScoreGreaterThanEqual(
            Long testId, Integer score1, Integer score2
    ); // 해당 점수 구간에 해당하는 레벨 조회
}