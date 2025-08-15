package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateRequestDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.*;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCoreCompetencyQuestionService {

    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final SubCompetencyCategoryRepository subCompetencyCategoryRepository; // 하위역량 조회용
    private final ResponseChoiceOptionRepository responseChoiceOptionRepository; // 선택지 관리용

    /**
     * [문항 생성]
     * - 평가와 하위 역량에 종속된 문항을 생성합니다.
     * - 생성 시점에는 선택지가 없으며, 옵션 개수 설정 API를 통해 추가됩니다.
     */
    @Transactional
    public CoreCompetencyQuestion createCoreCompetencyQuestion(Long assessmentId, CoreCompetencyQuestionCreateRequestDto dto) {

        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ASSESSMENT_NOT_FOUND));

        SubCompetencyCategory subCategory = subCompetencyCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.SUB_COMPETENCY_NOT_FOUND));

        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder()
                .assessment(assessment)
                .subCompetencyCategory(subCategory)
                .questionNo(dto.getQuestionNo())
                .name(dto.getQuestionName())
                .description(dto.getQuestionContent())
                .displayOrder(dto.getDisplayOrder())
                .answerAllowCount(1) // 기본값 1
                .optionCount(dto.getOptionCount())
                .build();

        return coreCompetencyQuestionRepository.save(question);

    }


    /**
     * [문항 수정]
     * - 문항의 기본 정보와 하위 역량을 수정합니다.
     * - 기존 선택지를 모두 삭제하고 요청받은 새 선택지로 교체하여 개수 변경에 유연하게 대응합니다.
     */

    @Transactional
    public CoreCompetencyQuestion updateCoreCompetencyQuestion(Long questionId, CoreCompetencyQuestionCreateRequestDto dto) {
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        // 기본 정보 수정
        question.setName(dto.getQuestionName());
        question.setDescription(dto.getQuestionContent());
        question.setDisplayOrder(dto.getDisplayOrder());
        question.setOptionCount(dto.getOptionCount());

        if (dto.getSelectAllowCount() != null) {
            question.setAnswerAllowCount(dto.getSelectAllowCount());
        }

        // 하위역량 변경
        if (dto.getSubCategoryId() != null) {
            SubCompetencyCategory subCategory = subCompetencyCategoryRepository.findById(dto.getSubCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.SUB_COMPETENCY_NOT_FOUND));
            question.setSubCompetencyCategory(subCategory);
        }

        // --- 선택지 수정 로직 ---

        // 2. 이 문항에 연결된 기존 선택지들을 모두 삭제합니다.
        responseChoiceOptionRepository.deleteByQuestion(question);

        // 3. DTO에 담겨온 새로운 선택지 정보로 새 ResponseChoiceOption 객체들을 만들어 저장합니다.
        if (dto.getOptions() != null && !dto.getOptions().isEmpty()) {
            List<ResponseChoiceOption> newOptions = dto.getOptions().stream()
                    .map(optionDto -> ResponseChoiceOption.builder() // <<< 실제 빌더 사용
                            .question(question)
                            .optionNo(optionDto.getOptionNo())
                            .label(optionDto.getLabel())
                            .score(optionDto.getScore())
                             .answerType("SINGLE") // 기본값이 있다면 빌더에서 생략 가능
                            .build())
                    .collect(Collectors.toList());

            responseChoiceOptionRepository.saveAll(newOptions);
        }

        return question;
    }

    /**
     * [선택지 개수 설정]
     * - 특정 문항의 선택지들을 모두 지우고, 요청된 개수만큼 기본 선택지를 새로 생성
     */
    @Transactional
    public CoreCompetencyQuestion setOptionCount(Long questionId, int newCount) {
        if (newCount < 1) {
            throw new CustomException(ErrorCode.INVALID_OPTION_COUNT);
        }

        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        question.getResponseChoiceOptions().clear();
        generateDefaultOptions(question, newCount);

        // 문항의 총 선택지 개수(optionCount) 필드를 업데이트
        question.setOptionCount(newCount);

        return coreCompetencyQuestionRepository.saveAndFlush(question);
    }


    /**
     * [문항 삭제]
     * - 문항을 삭제하고, 같은 그룹 내 다른 문항들의 번호를 재정렬합니다.
     */
    @Transactional
    public void deleteCoreCompetencyQuestion(Long questionId) {
        CoreCompetencyQuestion questionToDelete = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        Long assessmentId = questionToDelete.getAssessment().getId();
        Long subCategoryId = questionToDelete.getSubCompetencyCategory().getId();

        coreCompetencyQuestionRepository.delete(questionToDelete);

        // 삭제 후, 같은 평가와 하위역량에 속한 문항들의 번호를 재정렬
        reorderQuestions(assessmentId, subCategoryId);
    }

    /** [문항 단건 조회] */
    public CoreCompetencyQuestion getCoreCompetencyQuestion(Long questionId) {
        return coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }

    /** [전체 문항 조회] */
    public List<CoreCompetencyQuestion> getAllCoreCompetencyQuestions() {
        return coreCompetencyQuestionRepository.findAll();
    }


    /**
     * 특정 그룹 내 문항들의 번호와 순서를 1부터 다시 부여합니다.
     */
    private void reorderQuestions(Long assessmentId, Long subCategoryId) {
        List<CoreCompetencyQuestion> questionsToReorder =
                coreCompetencyQuestionRepository.findByAssessment_IdAndSubCompetencyCategory_IdOrderByDisplayOrderAsc(assessmentId, subCategoryId);

        int order = 1;
        for (CoreCompetencyQuestion question : questionsToReorder) {
            question.setQuestionNo(order);
            question.setDisplayOrder(order);
            order++;
        }
    }

    /**
     * 요청된 개수만큼 기본 선택지를 생성하여 문항에 추가합니다.
     */
    private void generateDefaultOptions(CoreCompetencyQuestion question, int count) {
        for (int i = 1; i <= count; i++) {
            ResponseChoiceOption option = ResponseChoiceOption.builder()
                    .question(question)
                    .optionNo(i)
                    .label("옵션 " + i)
                    .score(i)
                    .answerType("SINGLE") // 기본값 설정
                    .build();
            question.getResponseChoiceOptions().add(option);
        }
    }

    /**
     * 특정 평가에 속한 모든 문항 목록을 조회합니다.
     */
    public List<CoreCompetencyQuestion> getQuestionsByAssessmentId(Long assessmentId) {
        return coreCompetencyQuestionRepository.findQuestionsByAssessmentIdWithChoices(assessmentId);
    }

    /**
     * 특정 평가에 속한 모든 하위 역량 목록을 조회합니다.
     */
    public List<SubCompetencyCategory> getSubCategoriesByAssessmentId(Long assessmentId) {
        // Fetch Join을 사용하는 메소드를 호출
        List<CoreCompetencyCategory> coreCategories = coreCompetencyQuestionRepository.findCategoriesByAssessmentIdWithSubCategories(assessmentId);

        // 조회된 핵심역량 목록에서 하위역량 목록만 추출하여 반환
        return coreCategories.stream()
                .flatMap(c -> c.getSubCompetencyCategories().stream())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(SubCompetencyCategory::getId, sc -> sc, (a,b)->a, LinkedHashMap::new),
                        m -> new ArrayList<>(m.values())
                ));
    }
}

