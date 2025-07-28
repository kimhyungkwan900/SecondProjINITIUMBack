package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.user.diagnostic.dto.ScoreLevelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiagnosisAdvisorService {

    private final DiagnosisScoreService scoreService;

    public ScoreLevelDto getAdviceMessage(Long testId, int score) {
        String interpreted = scoreService.interpretScore(testId, score);
        String[] parts = interpreted.split(" - ", 2);

        return ScoreLevelDto.builder()
                .levelName(parts.length > 0 ? parts[0] : "")
                .description(parts.length > 1 ? parts[1] : "")
                .build();
    }
}