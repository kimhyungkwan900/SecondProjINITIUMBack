package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.AdminSubQuestionMappingDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminSubQuestionMappingService {

    private final SubCompetencyCategoryRepository subCompetencyCategoryRepository;
    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;

    /**
     * 하위역량 ID에 매핑된 문항 → 선택지 개수 정보를 조회
     */
    public AdminSubQuestionMappingDto getQuestionsBySubCompetency(Long subCategoryId) {
        // 1) 하위역량 조회
        SubCompetencyCategory subCategory = subCompetencyCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위역량입니다."));

        // 2) 하위역량 ID로 문항 직접 조회(정렬 포함)
        List<CoreCompetencyQuestion> questions = coreCompetencyQuestionRepository
                .findBySubCompetencyCategory_IdOrderByDisplayOrderAscQuestionNoAsc(subCategoryId);

        // 3) DTO 매핑 (선택지 개수 포함)
        List<AdminSubQuestionMappingDto.CoreCompetencyQuestionDto> questionDto = questions.stream()
                .map(q -> AdminSubQuestionMappingDto.CoreCompetencyQuestionDto.builder()
                        .id(q.getId())
                        .questionNo(q.getQuestionNo())
                        .questionName(q.getName())
                        .choiceCount(q.getOptionCount())
                        .build())
                .toList();

        // 4) 반환
        return AdminSubQuestionMappingDto.builder()
                .subCategoryId(subCategory.getId())
                .subCategoryName(subCategory.getSubCategoryName())
                .questions(questionDto)
                .build();
    }
}
