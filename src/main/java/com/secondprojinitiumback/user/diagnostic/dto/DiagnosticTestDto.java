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

    private Long id;                       // 조회 시 사용
    private String name;                  // 공통
    private String description;           // 공통
    private Boolean useYn;                // 공통 (등록/조회 둘 다 필요)

    // 문항 포함
    private List<DiagnosticQuestionDto> questions;

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

        return DiagnosticTestDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .useYn("Y".equalsIgnoreCase(entity.getUseYn()))
                .questions(questionDtos) // ✔ 문항까지 포함
                .build();
    }

}

