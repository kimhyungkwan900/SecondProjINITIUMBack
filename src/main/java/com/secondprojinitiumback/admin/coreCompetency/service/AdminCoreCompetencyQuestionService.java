package com.secondprojinitiumback.admin.coreCompetency.service;


import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateDto;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.repository.CommonCodeRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCoreCompetencyQuestionService {

    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final CommonCodeRepository commonCodeRepository;
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;

    //1. 문항 등록
    public CoreCompetencyQuestion createCoreCompetencyQuestion(Long assessmentId, CoreCompetencyQuestionCreateDto coreCompetencyQuestionCreateDto) {

        // 필수 필드 검증
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));

        CommonCode isCommonCode = commonCodeRepository.findByCdAndCdSe(coreCompetencyQuestionCreateDto.getIsCommonCode(),"COMMON_QUESTION")
                .orElseThrow(() -> new IllegalArgumentException("공통 여부 코드가 유효하지 않습니다."));

        // DTO를 엔티티로 변환
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder()
                .assessment(assessment)
                .questionNo(coreCompetencyQuestionCreateDto.getQuestionNo())
                .name(coreCompetencyQuestionCreateDto.getQuestionName())
                .displayOrder(coreCompetencyQuestionCreateDto.getDisplayOrder())
                .answerAllowCount(coreCompetencyQuestionCreateDto.getAnswerAllowCount())
                .isCommonCode(isCommonCode)
                .build();

        // 엔티티를 저장하고 반환
        return coreCompetencyQuestionRepository.save(question);

    }
    //2. 문항 수정
    public CoreCompetencyQuestion updateCoreCompetencyQuestion(Long questionId, CoreCompetencyQuestionCreateDto coreCompetencyQuestionCreateDto) {

        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));

        CommonCode isCommonCode = commonCodeRepository.findByCdAndCdSe(coreCompetencyQuestionCreateDto.getIsCommonCode(),"COMMON_QUESTION")
                .orElseThrow(() -> new IllegalArgumentException("공통 여부 코드가 유효하지 않습니다."));

        // 엔티티 업데이트
        question .setQuestionNo(coreCompetencyQuestionCreateDto.getQuestionNo());
        question.setName(coreCompetencyQuestionCreateDto.getQuestionName());
        question.setDisplayOrder(coreCompetencyQuestionCreateDto.getDisplayOrder());
        question.setAnswerAllowCount(coreCompetencyQuestionCreateDto.getAnswerAllowCount());
        question.setIsCommonCode(isCommonCode);

        // 엔티티를 저장하고 반환
        return coreCompetencyQuestionRepository.save(question);
    }

    //3. 문항 삭제
    public void deleteCoreCompetencyQuestion(Long questionId) {
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));
        coreCompetencyQuestionRepository.delete(question);
    }

    //4. 문항 조회
    public CoreCompetencyQuestion getCoreCompetencyQuestion(Long questionId) {
        return coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));
    }

    //5. 문항 전체 조회
    public List<CoreCompetencyQuestion> getAllCoreCompetencyQuestions() {
        return coreCompetencyQuestionRepository.findAll();
    }
}
