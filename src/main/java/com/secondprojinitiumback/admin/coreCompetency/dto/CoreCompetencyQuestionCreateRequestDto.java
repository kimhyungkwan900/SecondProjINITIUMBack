package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import lombok.*;

import java.util.List;

// 등록/수정 요청 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoreCompetencyQuestionCreateRequestDto {
    private Long assessmentId;
    private Long subCategoryId;              // 단일 ID
    private Integer questionNo;
    private String questionName;
    private String questionContent;
    private Integer displayOrder;
    private Integer optionCount;             // 선택지 개수
    private Integer selectAllowCount ;        // 선택가능횟수 -> 단일로 고정
    private List<ResponseChoiceOptionRequest> options; // label, score, 답변문항구분

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseChoiceOptionRequest {
        private Long id;
        private String label;
        private Integer score;
        private Integer optionNo;
    }
}

