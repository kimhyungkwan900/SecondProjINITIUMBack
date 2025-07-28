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

    private final DiagnosticScoreLevelRepository levelRepository;

    public int calculateTotalScore(List<DiagnosticResultDetail> details) {
        return details.stream()
                .mapToInt(DiagnosticResultDetail::getScore)
                .sum();
    }

    public String interpretScore(Long testId, int score) {
        return levelRepository.findByTestIdAndMinScoreLessThanEqualAndMaxScoreGreaterThanEqual(testId, score, score)
                .map(level -> level.getLevelName() + " - " + level.getDescription())
                .orElse("해석 불가 점수");
    }
}