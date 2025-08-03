package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 문항과 하위/핵심역량 매핑 정보 조회용 DTO
public class CategoryQuestionMappingDto {
    private Long mappingId;          // 매핑 고유 ID
    private Long subCategoryName;    // 하위역량 이름 (Long → String으로 수정 권장)
    private Long coreCategoryName;   // 핵심역량 이름 (Long → String으로 수정 권장)
    private Long questionId;         // 문항 ID
    private String questionName;     // 문항 내용
}