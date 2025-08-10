package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateRequestDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.*;
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

    /**
     * [문항 생성]
     * - 평가(assessmentId)와 하위역량(subCategoryId)을 지정하여 문항을 생성
     * - answerAllowCount(선택지 개수)가 있으면 해당 개수만큼 옵션(선택지) 자동 생성
     * - 초기 개수는 0도 허용 (추후 드롭다운 변경으로 옵션 생성 가능)
     */
    @Transactional
    public CoreCompetencyQuestion createCoreCompetencyQuestion(Long assessmentId, CoreCompetencyQuestionCreateRequestDto dto) {
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));
        if (dto.getSubCategoryId() == null) throw new IllegalArgumentException("하위역량 ID가 필요합니다.");
        SubCompetencyCategory sub = subCompetencyCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위역량입니다."));

        CoreCompetencyQuestion q = CoreCompetencyQuestion.builder()
                .assessment(assessment)
                .subCompetencyCategory(sub)
                .questionNo(dto.getQuestionNo())
                .name(dto.getQuestionName())
                .description(dto.getQuestionContent())
                .displayOrder(dto.getDisplayOrder())
                .answerAllowCount(1) // 단일 선택 고정
                .build();

        // 최초 생성 시 옵션은 0개로 두고,
        // 화면에서 드롭다운 변경(PATCH /option-count)으로 개수만큼 재생성하는 흐름.
        return coreCompetencyQuestionRepository.save(q);
    }


    /**
     * [문항 수정]
     * - 기본 정보(번호, 제목, 설명, 순서, 하위역량) 수정
     * - answerAllowCount(선택지 개수)는 수정 불가 (드롭다운 API에서만 변경 가능)
     * - 옵션은 추가/삭제 불가, 라벨·점수만 수정 가능
     */
    @Transactional
    public CoreCompetencyQuestion updateCoreCompetencyQuestion(Long questionId, CoreCompetencyQuestionCreateRequestDto dto) {
        CoreCompetencyQuestion q = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));

        q.setQuestionNo(dto.getQuestionNo());
        q.setName(dto.getQuestionName());
        q.setDescription(dto.getQuestionContent());
        q.setDisplayOrder(dto.getDisplayOrder());

        // 단일 고정 규칙 준수
        if (dto.getSelectAllowCount() != null && dto.getSelectAllowCount() != 1) {
            throw new IllegalArgumentException("선택가능횟수는 1(단일)만 허용됩니다.");
        }

        if (dto.getSubCategoryId() != null) {
            SubCompetencyCategory sub = subCompetencyCategoryRepository.findById(dto.getSubCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위역량입니다."));
            q.setSubCompetencyCategory(sub);
        }

        // 라벨/점수만 수정
        if (dto.getOptions() != null && !dto.getOptions().isEmpty()) {
            updateOptionLabelsAndScores(q, dto);
        }
        return coreCompetencyQuestionRepository.save(q);
    }


    /**
     * [드롭다운 변경 시]
     * - 특정 문항 1개의 옵션만 전부 삭제 후, 지정된 개수(newCount)로 재생성
     * - answerAllowCount 값도 갱신
     */
    @Transactional
    public CoreCompetencyQuestion setAnswerOptionCount(Long questionId, int newCount) {
        if (newCount < 1) throw new IllegalArgumentException("옵션 개수는 1 이상이어야 합니다.");
        CoreCompetencyQuestion q = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));

        resetOptions(q, newCount); // 기존 전부 제거 → newCount개 생성
        // q.setAnswerAllowCount(1); // 단일 고정, 굳이 다시 셋 필요 없음
        return q;
    }


    /**
     * [문항 삭제]
     * - 문항 삭제 후, 동일 평가 + 하위역량 묶음의 questionNo, displayOrder 재정렬
     */
    @Transactional
    public void deleteCoreCompetencyQuestion(Long questionId) {
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));

        Long assessmentId = (question.getAssessment() != null) ? question.getAssessment().getId() : null;
        Long subCategoryId = (question.getSubCompetencyCategory() != null) ? question.getSubCompetencyCategory().getId() : null;

        // 문항 삭제
        coreCompetencyQuestionRepository.delete(question);

        // 같은 묶음 문항들의 번호와 순서를 1부터 재부여
        if (assessmentId != null && subCategoryId != null) {
            List<CoreCompetencyQuestion> list =
                    coreCompetencyQuestionRepository.findByAssessment_IdAndSubCompetencyCategory_IdOrderByDisplayOrderAsc(assessmentId, subCategoryId);
            int i = 1;
            for (CoreCompetencyQuestion q : list) {
                q.setQuestionNo(i);
                q.setDisplayOrder(i);
                i++;
            }
        }
    }

    /** [문항 단건 조회] */
    public CoreCompetencyQuestion getCoreCompetencyQuestion(Long questionId) {
        return coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));
    }

    /** [전체 문항 조회] */
    public List<CoreCompetencyQuestion> getAllCoreCompetencyQuestions() {
        return coreCompetencyQuestionRepository.findAll();
    }


    /**
     * 옵션 전체 제거 후, 지정 개수로 재생성
     * - orphanRemoval=true 설정 시, remove() 호출로 DB에서 자동 삭제됨
     */
    private void resetOptions(CoreCompetencyQuestion q, int count) {
        if (q.getResponseChoiceOptions() == null) {
            q.setResponseChoiceOptions(new ArrayList<>());
        } else {
            Iterator<ResponseChoiceOption> it = q.getResponseChoiceOptions().iterator();
            while (it.hasNext()) {
                ResponseChoiceOption opt = it.next();
                opt.setQuestion(null);
                it.remove();
            }
        }
        addOptionsByCount(q, count);
    }

    /**
     * 지정 개수만큼 옵션 생성
     * - optionNo, 기본 라벨("옵션n"), 점수(no) 부여
     */
    private void addOptionsByCount(CoreCompetencyQuestion q, int count) {
        if (q.getResponseChoiceOptions() == null) q.setResponseChoiceOptions(new ArrayList<>());
        for (int no = 1; no <= count; no++) {
            q.getResponseChoiceOptions().add(
                    ResponseChoiceOption.builder()
                            .question(q)
                            .optionNo(no)
                            .label("옵션" + no)
                            .score(no) // 기본 점수 정책(필요 시 0으로)
                            .build()
            );
        }
    }

    /**
     * 옵션 라벨/점수만 수정
     * - 개수와 ID 동일성 검증
     * - 추가/삭제 불가
     */
    private void updateOptionLabelsAndScores(CoreCompetencyQuestion q, CoreCompetencyQuestionCreateRequestDto dto) {
        List<ResponseChoiceOption> existing = q.getResponseChoiceOptions();
        if (existing == null) throw new IllegalStateException("선택지 정보가 없습니다.");
        if (dto.getOptions().size() != existing.size()) {
            throw new IllegalArgumentException("선택지 개수는 변경할 수 없습니다.");
        }

        Map<Long, ResponseChoiceOption> byId = existing.stream()
                .collect(Collectors.toMap(ResponseChoiceOption::getId, o -> o));

        for (CoreCompetencyQuestionCreateRequestDto.ResponseChoiceOptionRequest odto : dto.getOptions()) {
            Long oid = odto.getId();
            if (oid == null || !byId.containsKey(oid)) {
                throw new IllegalArgumentException("선택지 ID가 일치하지 않습니다.");
            }
            ResponseChoiceOption target = byId.get(oid);

            // 라벨/점수만 수정
            if (odto.getLabel() != null) target.setLabel(odto.getLabel());
            if (odto.getScore() != null) target.setScore(odto.getScore());

            // optionNo는 보통 변경 안 하지만, 서버-클라 정렬 맞춤 용도로만 선택적 반영
            if (odto.getOptionNo() != null) target.setOptionNo(odto.getOptionNo());
        }
    }

}
