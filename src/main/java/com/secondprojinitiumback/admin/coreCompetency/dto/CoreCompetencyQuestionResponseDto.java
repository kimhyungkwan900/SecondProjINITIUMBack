// New File: CoreCompetencyQuestionResponseDto.java
package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CoreCompetencyQuestionResponseDto {

    private Long id;
    private Long assessmentId;
    private Long subCategoryId;
    private Integer questionNo;
    private String questionName;
    private String description;
    private Integer displayOrder;
    private Integer optionCount;
    private Integer selectAllowCount;
    private List<ResponseChoiceOptionDto> options; // 기존 DTO 재활용

    // Entity를 DTO로 변환하는 정적 팩토리 메소드
    public static CoreCompetencyQuestionResponseDto fromEntity(CoreCompetencyQuestion q) {
        return CoreCompetencyQuestionResponseDto.builder()
                .id(q.getId())
                .assessmentId(q.getAssessment().getId())
                .subCategoryId(q.getSubCompetencyCategory().getId())
                .questionNo(q.getQuestionNo())
                .questionName(q.getName())
                .description(q.getDescription())
                .displayOrder(q.getDisplayOrder())
                .optionCount(q.getOptionCount()) // 옵션 개수
                .selectAllowCount(q.getAnswerAllowCount())
                .options(q.getResponseChoiceOptions().stream()
                        .map(ResponseChoiceOptionDto::fromEntity) // 기존 DTO의 변환 메소드 사용
                        .collect(Collectors.toList()))
                .build();
    }
}