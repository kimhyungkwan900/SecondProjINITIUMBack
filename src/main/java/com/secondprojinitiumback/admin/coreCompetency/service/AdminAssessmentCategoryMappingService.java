package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAssessmentCategoryMappingService {

    private final CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;

    public List<CoreCompetencyCategoryDto> getCategoryStructureByAssessment(Long assessmentId) {
        return coreCompetencyCategoryRepository.findByAssessment_Id(assessmentId)
                .stream()
                .map(CoreCompetencyCategoryDto::fromEntity)
                .toList();
    }
}
