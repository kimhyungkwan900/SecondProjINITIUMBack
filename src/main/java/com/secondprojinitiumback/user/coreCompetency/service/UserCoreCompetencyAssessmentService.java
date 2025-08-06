package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyAssessmentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCoreCompetencyAssessmentService {

    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;

    public List<UserCoreCompetencyAssessmentDTO> getAllAssessment() {
        List<CoreCompetencyAssessment> assessments =
                coreCompetencyAssessmentRepository.findAll();

        return assessments.stream()
                .map(a -> UserCoreCompetencyAssessmentDTO.builder()
                        .id(a.getId())
                        .assessmentName(a.getAssessmentName())
                        .startDate(a.getStartDate())
                        .endDate(a.getEndDate())
                        .build()
                ).collect(Collectors.toList());
    }
}
