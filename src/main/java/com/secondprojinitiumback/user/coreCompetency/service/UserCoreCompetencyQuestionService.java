package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCoreCompetencyQuestionService {

    // 핵심역량 문항 레포지토리 (문항 및 선택지를 조회할 때 사용)
    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;

    /**
     * 주어진 평가 ID에 해당하는 문항 목록을 조회하여 DTO 리스트로 반환
     * - 문항에 연결된 선택지까지 함께 가져오는 쿼리를 사용
     */
    public List<UserCoreCompetencyQuestionDto> getQuestionsByAssessment(Long assessmentId) {
        // 1. 평가 ID에 해당하는 모든 문항 + 선택지 리스트 조회
        List<CoreCompetencyQuestion> list = coreCompetencyQuestionRepository.findAllWithOptionsByAssessmentId(assessmentId);

        // 2. 엔티티 리스트를 DTO 리스트로 변환하여 반환
        return list.stream()
                .map(UserCoreCompetencyQuestionDto::fromEntity) // 각 문항을 DTO로 변환
                .toList();
    }
}
