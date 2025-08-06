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

    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;

    public List<UserCoreCompetencyQuestionDto> getQuestionsByAssessment(Long assessmentId) {
        List<CoreCompetencyQuestion> list = coreCompetencyQuestionRepository.findByAssessment_Id(assessmentId);
        return list.stream()
                .map(UserCoreCompetencyQuestionDto::fromEntity).toList();
    }
}
