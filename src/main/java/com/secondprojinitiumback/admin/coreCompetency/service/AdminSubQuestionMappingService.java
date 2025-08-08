package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.AdminSubQuestionMappingDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.BehaviorIndicatorRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSubQuestionMappingService {

    private final SubCompetencyCategoryRepository subCompetencyCategoryRepository;
    private final BehaviorIndicatorRepository behaviorIndicatorRepository;

    /**
     * 하위역량 ID에 해당하는 행동지표 → 문항 → 선택지 개수 정보를 조회
     */
    public AdminSubQuestionMappingDto getQuestionsBySubCompetency(Long subCategoryId) {

        // 1. 하위역량 정보 조회
        SubCompetencyCategory subCategory = subCompetencyCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위역량입니다."));

        // 2. 해당 하위역량에 연결된 행동지표 목록 조회
        List<BehaviorIndicator> indicators = behaviorIndicatorRepository.findBySubCompetencyCategory_Id(subCategoryId);

        // 3. 행동지표로부터 문항 추출 (중복 제거) 및 선택지 개수 포함
        List<AdminSubQuestionMappingDto.CoreCompetencyQuestionDto> questions = indicators.stream()
                .map(BehaviorIndicator::getQuestions)
                .distinct()
                .map(q -> AdminSubQuestionMappingDto.CoreCompetencyQuestionDto.builder()
                        .id(q.getId())
                        .questionNo(q.getQuestionNo())
                        .questionName(q.getName())
                        .choiceCount(q.getResponseChoiceOptions() != null ? q.getResponseChoiceOptions().size() : 0)
                        .build())
                .toList();

        // 4. 하위역량 이름 + 문항 리스트 DTO로 반환
        return AdminSubQuestionMappingDto.builder()
                .subCategoryId(subCategory.getId())
                .subCategoryName(subCategory.getSubCategoryName())
                .questions(questions)
                .build();
    }
}
