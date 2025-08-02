package com.secondprojinitiumback.user.diagnostic.dto;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticTestDto {

    private Long id;
    private String name;
    private String description;
    private Boolean useYn;

    private List<DiagnosticQuestionDto> questions; // 기존 문항 리스트
    private List<ScoreLevelDto> scoreLevels;       // 🔹 점수 구간 해석 리스트 추가

    public static DiagnosticTestDto from(DiagnosticTest entity) {
        List<DiagnosticQuestionDto> questionDtos = entity.getQuestions().stream()
                .map(q -> DiagnosticQuestionDto.builder()
                        .id(q.getId())
                        .content(q.getContent())
                        .order(q.getOrder())
                        .answerType(q.getAnswerType().name())
                        .answers(q.getAnswers().stream().map(a -> DiagnosticAnswerDto.builder()
                                .id(a.getId())
                                .content(a.getContent())
                                .score(a.getScore())
                                .selectValue(a.getSelectValue())
                                .build()).toList())
                        .build())
                .toList();

        List<ScoreLevelDto> scoreLevelDtos = entity.getScoreLevels().stream()
                .map(s -> ScoreLevelDto.builder()
                        .levelName(s.getLevelName())
                        .description(s.getDescription())
                        .build())
                .toList();

        return DiagnosticTestDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .useYn("Y".equalsIgnoreCase(entity.getUseYn()))
                .questions(questionDtos)
                .scoreLevels(scoreLevelDtos) // 🔹 점수 해석 포함
                .build();
    }
}


