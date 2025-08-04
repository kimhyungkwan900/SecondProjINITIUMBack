//package com.secondprojinitiumback.admin.coreCompetency.service;
//
//import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
//import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
//import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
//import com.secondprojinitiumback.admin.coreCompetency.dto.IdealTalentProfileDto;
//import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
//import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
//import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class IdealTalentProfileService {
//
//    private final IdealTalentProfileRepository idealTalentProfileRepository;
//    private final CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;
//    private final SubCompetencyCategoryRepository subCompetencyCategoryRepository;
//
//
//    public List<IdealTalentProfileDto> getProfileSummary() {
//        List<IdealTalentProfile> profiles = idealTalentProfileRepository.findAll();
//
//        return profiles.stream().map(profile ->{
//            List<CoreCompetencyCategory> coreCompetencyCategoryList = coreCompetencyCategoryRepository.findByIdealTalentProfile_Id(profile.getId());
//
//            List<IdealTalentProfileDto.CoreCompetencyCategoryDto> coreCompetencyCategoryDtos = coreCompetencyCategoryList.stream().map(core->{
//                List<SubCompetencyCategory> subList  = subCompetencyCategoryRepository.findByParent(core);
//            })
//        })
//    }
//}
