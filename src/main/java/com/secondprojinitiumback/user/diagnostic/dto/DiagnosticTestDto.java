package com.secondprojinitiumback.user.diagnostic.dto;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticTestDto {

    private Long id;
    private String name;
    private String description;

    private List<DiagnosticQuestionDto> questions;
    private List<ScoreLevelDto> scoreLevels;

    public static DiagnosticTestDto from(DiagnosticTest entity) {
        // 질문 DTO 변환 (null-safe)
        List<DiagnosticQuestionDto> questionDtos = Optional.ofNullable(entity.getQuestions())
                .orElse(List.of())
                .stream()
                .map(q -> DiagnosticQuestionDto.builder()
                        .id(q.getId())
                        .content(q.getContent())
                        .order(q.getOrder())
                        .answerType(q.getAnswerType() != null ? q.getAnswerType().name() : null)
                        .answers(Optional.ofNullable(q.getAnswers())
                                .orElse(List.of())
                                .stream()
                                .map(a -> DiagnosticAnswerDto.builder()
                                        .id(a.getId())
                                        .content(a.getContent())
                                        .score(a.getScore())
                                        .selectValue(a.getSelectValue())
                                        .build())
                                .toList())
                        .build())
                .toList();

        // 점수 구간 DTO 변환 (min/max 누락 보완)
        List<ScoreLevelDto> scoreLevelDtos = Optional.ofNullable(entity.getScoreLevels())
                .orElse(List.of())
                .stream()
                .map(s -> ScoreLevelDto.builder()
                        .minScore(s.getMinScore())      // ✅ 추가
                        .maxScore(s.getMaxScore())      // ✅ 추가
                        .levelName(s.getLevelName())
                        .description(s.getDescription())
                        .build())
                .toList();

        return DiagnosticTestDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .questions(questionDtos)
                .scoreLevels(scoreLevelDtos)
                .build();
    }
}