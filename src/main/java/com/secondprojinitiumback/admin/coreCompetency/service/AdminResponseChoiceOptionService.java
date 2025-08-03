package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.ResponseChoiceOption;
import com.secondprojinitiumback.admin.coreCompetency.dto.ResponseChoiceOptionDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.ResponseChoiceOptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 관리자용 서비스 클래스 - 문항에 대한 응답 선택지(ResponseChoiceOption)를 등록/수정/삭제/조회함
 */
@Service
@RequiredArgsConstructor
public class AdminResponseChoiceOptionService {

    private final ResponseChoiceOptionRepository responseChoiceOptionRepository;
    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;

    /**
     * 특정 문항에 대해 여러 개의 선택지를 생성하여 저장함
     * @param questionId 문항 ID
     * @param dtos 등록할 선택지 DTO 리스트
     */
    @Transactional
    public void createOptions(Long questionId, List<ResponseChoiceOptionDto> dtos) {
        // 문항 ID를 기반으로 문항 조회 (존재하지 않으면 예외 발생)
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("문항 없음"));

        // 각 DTO를 ResponseChoiceOption 엔티티로 변환하여 저장
        for (ResponseChoiceOptionDto dto : dtos) {
            ResponseChoiceOption option = ResponseChoiceOption.builder()
                    .question(question) // 연관된 문항 설정
                    .label(dto.getLabel()) // 보기 라벨
                    .score(dto.getScore()) // 보기 점수
                    .build();
            responseChoiceOptionRepository.save(option);
        }
    }

    /**
     * 선택지 ID를 기반으로 특정 선택지 정보를 수정함
     * @param optionId 선택지 ID
     * @param dto 수정할 선택지 정보
     */
    @Transactional
    public void updateOption(Long optionId, ResponseChoiceOptionDto dto) {
        // 선택지 조회 (없으면 예외)
        ResponseChoiceOption option = responseChoiceOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("선택지 없음"));

        // 라벨 및 점수 수정
        option.setLabel(dto.getLabel());
        option.setScore(dto.getScore());

        // JPA 영속성 컨텍스트에 의해 자동 반영되지만 명시적으로 저장
        responseChoiceOptionRepository.save(option);
    }

    /**
     * 선택지 ID를 기반으로 해당 선택지를 삭제함
     * @param optionId 삭제할 선택지 ID
     */
    @Transactional
    public void deleteOption(Long optionId) {
        responseChoiceOptionRepository.deleteById(optionId);
    }

    /**
     * 특정 문항에 연결된 모든 선택지를 조회함
     * @param questionId 문항 ID
     * @return 해당 문항에 대한 선택지 목록 (DTO 형식)
     */
    public List<ResponseChoiceOptionDto> getOptionsByQuestionId(Long questionId) {
        // question.id 기준으로 보기 목록 조회
        return responseChoiceOptionRepository.findByQuestion_Id(questionId)
                .stream()
                .map(option -> new ResponseChoiceOptionDto(
                        option.getId(),
                        option.getLabel(),
                        option.getScore()))
                .toList();
    }
}
