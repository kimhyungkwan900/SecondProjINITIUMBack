package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCoreCompetencyQuestionService {

    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;

    /**
     * [1] 핵심역량 문항 등록
     * - 특정 진단평가(assessmentId)에 문항을 추가
     * - DTO를 기반으로 CoreCompetencyQuestion 엔티티를 생성 후 저장
     */
    @Transactional
    public CoreCompetencyQuestion createCoreCompetencyQuestion(Long assessmentId, CoreCompetencyQuestionDto coreCompetencyQuestionDto) {

        // 진단평가 ID로 CoreCompetencyAssessment 조회 (없으면 예외 발생)
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));

        // DTO → 엔티티 변환
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder()
                .assessment(assessment)
                .questionNo(coreCompetencyQuestionDto.getQuestionNo())
                .name(coreCompetencyQuestionDto.getQuestionName())
                .description(coreCompetencyQuestionDto.getQuestionContent())
                .displayOrder(coreCompetencyQuestionDto.getDisplayOrder())
                .answerAllowCount(coreCompetencyQuestionDto.getAnswerAllowCount())
                .build();

        // 문항 저장 후 반환
        return coreCompetencyQuestionRepository.save(question);
    }

    /**
     * [2] 문항 수정
     * - 기존 문항 ID로 문항 조회 후, 전달받은 DTO 값으로 필드 수정
     */
    @Transactional
    public CoreCompetencyQuestion updateCoreCompetencyQuestion(Long questionId, CoreCompetencyQuestionDto coreCompetencyQuestionDto) {

        // 기존 문항 조회 (없으면 예외 발생)
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));

        // 필드 업데이트
        question.setQuestionNo(coreCompetencyQuestionDto.getQuestionNo());
        question.setName(coreCompetencyQuestionDto.getQuestionName());
        question.setDescription(coreCompetencyQuestionDto.getQuestionContent());
        question.setDisplayOrder(coreCompetencyQuestionDto.getDisplayOrder());
        question.setAnswerAllowCount(coreCompetencyQuestionDto.getAnswerAllowCount());

        // 수정된 문항 저장 후 반환
        return coreCompetencyQuestionRepository.save(question);
    }

    /**
     * [3] 문항 삭제
     * - 문항 ID로 조회 후 삭제 처리
     */
    @Transactional
    public void deleteCoreCompetencyQuestion(Long questionId) {
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));

        coreCompetencyQuestionRepository.delete(question);
    }

    /**
     * [4] 문항 단건 조회
     * - 문항 ID를 기반으로 단일 문항 정보 반환
     */
    public CoreCompetencyQuestion getCoreCompetencyQuestion(Long questionId) {
        return coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));
    }

    /**
     * [5] 전체 문항 목록 조회
     * - 관리자 페이지 등에서 문항 리스트 출력용
     */
    public List<CoreCompetencyQuestion> getAllCoreCompetencyQuestions() {
        return coreCompetencyQuestionRepository.findAll();
    }
}
