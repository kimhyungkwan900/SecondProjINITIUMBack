package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.IdealTalentProfileDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCompetencyCategoryService {

    private final CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;
    private final IdealTalentProfileRepository idealTalentProfileRepository;
    private final SubCompetencyCategoryRepository subCompetencyCategoryRepository;
    private final CoreCompetencyAssessmentRepository  coreCompetencyAssessmentRepository;
    private final CommonCodeRepository commonCodeRepository;


    // 0. 모든 인재상 목록을 DTO로 조회하는 메서드 추가
    public List<IdealTalentProfileDto> getAllIdealTalentProfiles() {
        return idealTalentProfileRepository.findAll().stream()
                .map(IdealTalentProfileDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 1. 역량 카테고리 등록
    @Transactional
    public void createCategory(CompetencyCategoryDto dto) {

        if (dto.getCompetencyCategory().getCodeName().equalsIgnoreCase("핵심역량")) {
            // 핵심역량 등록
            IdealTalentProfile idealTalentProfile = idealTalentProfileRepository.findById(dto.getIdealTalentProfileId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인재상입니다."));

            if (dto.getAssessmentId() == null) {
                throw new IllegalArgumentException("진단평가 ID가 필요합니다.");
            }
            CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(dto.getAssessmentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 진단평가입니다."));

            CommonCode coreCompCode = commonCodeRepository.findById_CodeAndId_CodeGroup("C", "COMP")
                    .orElseThrow(() -> new IllegalArgumentException("핵심역량 공통 코드를 찾을 수 없습니다."));

            CoreCompetencyCategory core = CoreCompetencyCategory.builder()
                    .coreCategoryName(dto.getName())
                    .coreCategoryNote(dto.getDescription())
                    .idealTalentProfile(idealTalentProfile)
                    .assessment(assessment)
                    .competencyCategory(coreCompCode)
                    .build();

            coreCompetencyCategoryRepository.save(core);

        } else {
            // 하위역량 등록
            CoreCompetencyCategory parent = coreCompetencyCategoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));

            CommonCode coreCompCode = commonCodeRepository.findById_CodeAndId_CodeGroup("S", "COMP")
                    .orElseThrow(() -> new IllegalArgumentException("하위역량 공통 코드를 찾을 수 없습니다."));

            SubCompetencyCategory sub = SubCompetencyCategory.builder()
                    .subCategoryName(dto.getName())
                    .subCategoryNote(dto.getDescription())
                    .coreCompetencyCategory(parent)
                    .competencyCategory(coreCompCode)
                    .build();

            subCompetencyCategoryRepository.save(sub);
        }
    }

    // 2. 역량 카테고리 수정
    @Transactional
    public void updateCategory(Long id, CompetencyCategoryDto dto) {

        if (dto.getCompetencyCategory().getCodeName().equalsIgnoreCase("핵심역량")) {
            // 핵심역량 수정
            CoreCompetencyCategory core = coreCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));

            IdealTalentProfile idealTalentProfile = idealTalentProfileRepository.findById(dto.getIdealTalentProfileId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인재상입니다."));

            if (dto.getAssessmentId() == null) {
                throw new IllegalArgumentException("진단평가 ID가 필요합니다.");
            }
            CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(dto.getAssessmentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 진단평가입니다."));

            core.setCoreCategoryName(dto.getName());
            core.setCoreCategoryNote(dto.getDescription());
            core.setIdealTalentProfile(idealTalentProfile);
            core.setAssessment(assessment);

            coreCompetencyCategoryRepository.save(core);

        } else {
            // 하위역량 수정
            SubCompetencyCategory sub = subCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위 역량 카테고리입니다."));

            sub.setSubCategoryName(dto.getName());
            sub.setSubCategoryNote(dto.getDescription());

            subCompetencyCategoryRepository.save(sub);
        }
    }

    // 3. 역량 카테고리 삭제
    @Transactional
    public void deleteCategory(Long id, CompetencyCategoryDto dto) {
        if (dto.getCompetencyCategory().getCodeName().equalsIgnoreCase("핵심역량")) {
            CoreCompetencyCategory core = coreCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("핵심역량 없음"));
            coreCompetencyCategoryRepository.delete(core);
        } else {
            SubCompetencyCategory sub = subCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("하위역량 없음"));
            subCompetencyCategoryRepository.delete(sub);
        }
    }

    // 4. 전체 조회
    public List<CoreCompetencyCategory> getAllCoreCompetencyCategories() {
        return coreCompetencyCategoryRepository.findAll();
    }
    public List<SubCompetencyCategory> getAllSubCompetencyCategories() {
        return subCompetencyCategoryRepository.findAll();
    }

    // 5. 상세 조회
    public CoreCompetencyCategory getCoreCategory(Long id) {
        return coreCompetencyCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("핵심역량 없음"));
    }
    public SubCompetencyCategory getSubCategory(Long id) {
        return subCompetencyCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("하위역량 없음"));
    }

    // 6. 중복 체크
    public boolean isCoreCategoryNameDuplicate(String name) {
        return coreCompetencyCategoryRepository.existsByCoreCategoryName(name);
    }

    public boolean isSubCategoryNameDuplicate(Long coreCategoryId, String name) {
        return subCompetencyCategoryRepository.findByCoreCompetencyCategory_Id(coreCategoryId).stream()
                .anyMatch(sub -> sub.getSubCategoryName().equalsIgnoreCase(name));
    }

    // 7. 핵심역량에 속한 하위역량
    public List<SubCompetencyCategory> getSubCategoriesByCoreId(Long coreId) {
        return subCompetencyCategoryRepository.findByCoreCompetencyCategory_Id(coreId);
    }

    // 8. 진단에 속한 하위역량
    public List<SubCompetencyCategory> getSubCategoriesByAssessmentId(Long assessmentId) {
        List<CoreCompetencyCategory> coreCategories =
                coreCompetencyCategoryRepository.findByAssessment_Id(assessmentId);

        List<Long> categoryIds = coreCategories.stream()
                .map(CoreCompetencyCategory::getId)
                .toList();

        return subCompetencyCategoryRepository.findByCoreCompetencyCategory_IdIn(categoryIds);
    }
}
