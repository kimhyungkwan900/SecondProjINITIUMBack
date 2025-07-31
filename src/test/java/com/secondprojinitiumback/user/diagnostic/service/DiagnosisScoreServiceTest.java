package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticScoreLevel;
import com.secondprojinitiumback.user.diagnostic.repository.DiagnosticScoreLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DiagnosisScoreServiceTest {
    @Mock
    private DiagnosticScoreLevelRepository levelRepository;

    @InjectMocks
    private DiagnosisScoreService scoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }

    @Test
    void calculateTotalScore_정상합계계산() {
        // given
        DiagnosticResultDetail detail1 = DiagnosticResultDetail.builder().score(10).build();
        DiagnosticResultDetail detail2 = DiagnosticResultDetail.builder().score(20).build();
        DiagnosticResultDetail detail3 = DiagnosticResultDetail.builder().score(15).build();

        List<DiagnosticResultDetail> details = Arrays.asList(detail1, detail2, detail3);

        // when
        int totalScore = scoreService.calculateTotalScore(details);

        // then
        assertThat(totalScore).isEqualTo(45); // 10+20+15
    }

    @Test
    void interpretScore_해석존재시_정상반환() {
        // given
        Long testId = 1L;
        int score = 75;
        DiagnosticScoreLevel mockLevel = DiagnosticScoreLevel.builder()
                .levelName("중간 단계")
                .description("중간 수준의 점수입니다.")
                .build();

        when(levelRepository.findByTestIdAndMinScoreLessThanEqualAndMaxScoreGreaterThanEqual(testId, score, score))
                .thenReturn(Optional.of(mockLevel));

        // when
        String interpretation = scoreService.interpretScore(testId, score);

        // then
        assertThat(interpretation).isEqualTo("중간 단계 - 중간 수준의 점수입니다.");
    }

    @Test
    void interpretScore_해석없을때_해석불가문구반환() {
        // given
        Long testId = 1L;
        int score = 200;

        when(levelRepository.findByTestIdAndMinScoreLessThanEqualAndMaxScoreGreaterThanEqual(testId, score, score))
                .thenReturn(Optional.empty());

        // when
        String interpretation = scoreService.interpretScore(testId, score);

        // then
        assertThat(interpretation).isEqualTo("해석 불가 점수");
    }
}