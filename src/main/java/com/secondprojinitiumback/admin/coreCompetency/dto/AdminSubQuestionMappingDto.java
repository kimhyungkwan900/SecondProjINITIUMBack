package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator;
import jdk.jshell.Snippet;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSubQuestionMappingDto {

    private Long subCategoryId;         // 하위역량 ID
    private String subCategoryName;     // 하위역량 이름
    private List<CoreCompetencyQuestionDto> questions; // 하위역량에 속한 문항들

    // 내부 정적 클래스: 문항 DTO
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoreCompetencyQuestionDto {
        private Long id;                // 문항 ID
        private Integer questionNo;    // 문항 번호
        private String questionName;   // 문항 이름
        private int choiceCount;       // 선택지 개수

    }
}

