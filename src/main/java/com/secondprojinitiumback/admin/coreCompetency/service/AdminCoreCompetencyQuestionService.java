package com.secondprojinitiumback.admin.coreCompetency.service;


import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateDto;
import com.secondprojinitiumback.admin.coreCompetency.entity.BehaviorIndicator;
import com.secondprojinitiumback.admin.coreCompetency.entity.BehaviorIndicatorMajorQuestionMapping;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.repository.BehaviorIndicatorMajorQuestionMappingRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.BehaviorIndicatorRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminCoreCompetencyQuestionService {

    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final CommonCodeRepository commonCodeRepository;
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final BehaviorIndicatorRepository behaviorIndicatorRepository;
    private final BehaviorIndicatorMajorQuestionMappingRepository behaviorIndicatorMajorQuestionMappingRepository;

    //1. 문항 등록
    @Transactional
    public CoreCompetencyQuestion createCoreCompetencyQuestion(Long assessmentId, CoreCompetencyQuestionCreateDto coreCompetencyQuestionCreateDto) {

        // 필수 필드 검증
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심 역량 진단입니다."));


        // DTO를 엔티티로 변환
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder()
                .assessment(assessment)
                .questionNo(coreCompetencyQuestionCreateDto.getQuestionNo())
                .name(coreCompetencyQuestionCreateDto.getQuestionName())
                .displayOrder(coreCompetencyQuestionCreateDto.getDisplayOrder())
                .answerAllowCount(coreCompetencyQuestionCreateDto.getAnswerAllowCount())
                .build();

        // 엔티티를 저장
        CoreCompetencyQuestion savedQuestion = coreCompetencyQuestionRepository.save(question);

        //공통 문항이 아닌 경우, 행동지표랑 학과(전공) 매핑 추가
        if("N".equalsIgnoreCase(coreCompetencyQuestionCreateDto.getIsCommonCode()) && coreCompetencyQuestionCreateDto.getSubjectCode() != null){

            SchoolSubject schoolSubject = schoolSubjectRepository.findByCode(coreCompetencyQuestionCreateDto.getSubjectCode())
                    .orElseThrow(() -> new IllegalArgumentException("해당 학과코드가 존재하지 않습니다."));

            BehaviorIndicator behaviorIndicator = behaviorIndicatorRepository.findById(coreCompetencyQuestionCreateDto.getIndicatorId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 행동지표입니다."));

            // 기존 매핑이 없다면 새로 생성, 있다면 오류메세지 출력
            BehaviorIndicatorMajorQuestionMapping existingMapping = behaviorIndicatorMajorQuestionMappingRepository.findByQuestionAndBehaviorIndicatorAndSchoolSubject(
                    savedQuestion, behaviorIndicator, schoolSubject);

            if(existingMapping == null){
                // 매핑이 없다면 새로 생성
                BehaviorIndicatorMajorQuestionMapping newMapping = BehaviorIndicatorMajorQuestionMapping.builder()
                        .question(savedQuestion)
                        .behaviorIndicator(behaviorIndicator)
                        .schoolSubject(schoolSubject)
                        .build();
                behaviorIndicatorMajorQuestionMappingRepository.save(newMapping);
            }else {
                // 매핑이 이미 존재한다면 예외 처리
                throw new IllegalArgumentException("이미 해당 행동지표와 학과(전공)에 매핑된 문항이 존재합니다.");
            }
        }
        return  coreCompetencyQuestionRepository.save(savedQuestion);
    }

    //2. 문항 수정
    @Transactional
    public CoreCompetencyQuestion updateCoreCompetencyQuestion(Long questionId, CoreCompetencyQuestionCreateDto coreCompetencyQuestionCreateDto) {

        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));


        // 엔티티 업데이트
        question.setQuestionNo(coreCompetencyQuestionCreateDto.getQuestionNo());
        question.setName(coreCompetencyQuestionCreateDto.getQuestionName());
        question.setDisplayOrder(coreCompetencyQuestionCreateDto.getDisplayOrder());
        question.setAnswerAllowCount(coreCompetencyQuestionCreateDto.getAnswerAllowCount());


        // 엔티티를 저장하고 반환
        CoreCompetencyQuestion savedQuestion = coreCompetencyQuestionRepository.save(question);

        if("N".equalsIgnoreCase(coreCompetencyQuestionCreateDto.getIsCommonCode()) && coreCompetencyQuestionCreateDto.getSubjectCode() != null){
            BehaviorIndicator behaviorIndicator = behaviorIndicatorRepository.findById(coreCompetencyQuestionCreateDto.getIndicatorId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 행동지표입니다."));
            SchoolSubject schoolSubject = schoolSubjectRepository.findByCode(coreCompetencyQuestionCreateDto.getSubjectCode())
                    .orElseThrow(() -> new IllegalArgumentException("해당 학과코드가 존재하지 않습니다."));

            // 기존 매핑이 있다면 업데이트, 없다면 새로 생성
            BehaviorIndicatorMajorQuestionMapping existingMapping = behaviorIndicatorMajorQuestionMappingRepository.findByQuestionAndBehaviorIndicatorAndSchoolSubject(
                    savedQuestion, behaviorIndicator, schoolSubject);

            if(existingMapping != null) {
               existingMapping.setQuestion(savedQuestion);
                existingMapping.setBehaviorIndicator(behaviorIndicator);
                existingMapping.setSchoolSubject(schoolSubject);
                behaviorIndicatorMajorQuestionMappingRepository.save(existingMapping);
            }else {
                // 새로운 매핑 생성
                BehaviorIndicatorMajorQuestionMapping mapping = BehaviorIndicatorMajorQuestionMapping.builder()
                        .question(savedQuestion)
                        .behaviorIndicator(behaviorIndicator)
                        .schoolSubject(schoolSubject)
                        .build();

                behaviorIndicatorMajorQuestionMappingRepository.save(mapping);
            }
        }
        return savedQuestion;
    }

    //3. 문항 삭제
    @Transactional
    public void deleteCoreCompetencyQuestion(Long questionId) {
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));
        coreCompetencyQuestionRepository.delete(question);
        //관련된 매핑이나 응답 데이터 삭제는 포함 시킬 예정
    }

    //4. 문항 상세 조회
    public CoreCompetencyQuestion getCoreCompetencyQuestion(Long questionId) {
        return coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문항입니다."));
    }

    //5. 문항 전체 조회
    public List<CoreCompetencyQuestion> getAllCoreCompetencyQuestions() {
        return coreCompetencyQuestionRepository.findAll();
    }
}
