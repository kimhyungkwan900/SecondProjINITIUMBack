package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 핵심역량 및 하위역량 카테고리 서비스 테스트 클래스
class AdminCompetencyCategoryServiceTest {

    // 핵심역량 저장소 Mock
    @Mock
    private CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;

    // 인재상 저장소 Mock
    @Mock
    private IdealTalentProfileRepository idealTalentProfileRepository;

    // 하위역량 저장소 Mock
    @Mock
    private SubCompetencyCategoryRepository subCompetencyCategoryRepository;

    // 테스트 대상 서비스에 Mock 주입
    @InjectMocks
    private AdminCompetencyCategoryService categoryService;

    @Test
    @DisplayName("핵심역량 카테고리 등록 테스트")
    void createCategoryTest() {
        // Given: 핵심역량 생성 요청 DTO와 인재상 객체를 구성하여 등록 테스트 준비
        CompetencyCategoryDto competencyCategoryDto = new CompetencyCategoryDto();
        competencyCategoryDto.setName("창의역량");
        competencyCategoryDto.setDescription("창의적 문제 해결 능력");
        competencyCategoryDto.setLevelType("CORE_COMPETENCY");
        competencyCategoryDto.setIdealTalentProfileId(1L);

        IdealTalentProfile idealTalentProfile = IdealTalentProfile.builder().id(1L).build();
        when(idealTalentProfileRepository.findById(1L)).thenReturn(Optional.of(idealTalentProfile));

        // When: 핵심역량 등록 메서드 호출
        categoryService.createCategory(competencyCategoryDto);

        // Then: 핵심역량 저장 메서드가 1회 호출되었는지 검증
        verify(coreCompetencyCategoryRepository, times(1)).save(any(CoreCompetencyCategory.class));
    }

    @Test
    @DisplayName("하위역량 카테고리 등록 테스트")
    void createSubCategoryTest() {
        // Given: 하위역량 생성 요청 DTO와 상위 카테고리 객체를 구성하여 등록 테스트 준비
        CompetencyCategoryDto competencyCategoryDto = new CompetencyCategoryDto();
        competencyCategoryDto.setName("창의적 사고");
        competencyCategoryDto.setDescription("창의적 문제 해결 능력 향상");
        competencyCategoryDto.setLevelType("SUB_COMPETENCY");
        competencyCategoryDto.setParentId(1L);

        CoreCompetencyCategory coreCompetencyCategory = CoreCompetencyCategory.builder().id(1L).build();
        when(coreCompetencyCategoryRepository.findById(1L)).thenReturn(Optional.of(coreCompetencyCategory));

        // When: 하위역량 등록 메서드 호출
        categoryService.createCategory(competencyCategoryDto);

        // Then: 하위역량 저장 메서드가 1회 호출되었는지 검증
        verify(subCompetencyCategoryRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("핵심역량 카테고리 수정 테스트")
    void updateCategoryTest() {
        // Given: 기존 핵심역량과 수정 요청 DTO, 인재상 객체를 구성하여 수정 테스트 준비
        Long CoreCategoryId = 1L;
        CoreCompetencyCategory existingCompetencyCategory = CoreCompetencyCategory.builder().id(CoreCategoryId).build();
        IdealTalentProfile idealTalentProfile = IdealTalentProfile.builder().id(10L).build();

        CompetencyCategoryDto competencyCategoryDto = new CompetencyCategoryDto();
        competencyCategoryDto.setName("수정한 역량");
        competencyCategoryDto.setDescription("수정한 역량 설명");
        competencyCategoryDto.setLevelType("CORE_COMPETENCY");
        competencyCategoryDto.setIdealTalentProfileId(1L);

        when(coreCompetencyCategoryRepository.findById(CoreCategoryId)).thenReturn(Optional.of(existingCompetencyCategory));
        when(idealTalentProfileRepository.findById(1L)).thenReturn(Optional.of(idealTalentProfile));

        // When: 수정 메서드 호출
        categoryService.updateCategory(CoreCategoryId, competencyCategoryDto);

        // Then: 필드 값이 DTO와 일치하게 변경되었는지 확인 및 저장 메서드 호출 검증
        assertEquals("수정한 역량", existingCompetencyCategory.getCoreCategoryName());
        assertEquals("수정한 역량 설명", existingCompetencyCategory.getCoreCategoryNote());
        verify(coreCompetencyCategoryRepository, times(1)).save(existingCompetencyCategory);
    }

    @Test
    @DisplayName("하위역량 카테고리 수정 테스트")
    void updateSubCategoryTest() {
        // Given: 기존 하위역량과 수정 요청 DTO를 구성하여 수정 테스트 준비
        Long subCategoryId = 1L;
        SubCompetencyCategory existingSubCategory = SubCompetencyCategory.builder().id(subCategoryId).build();

        CompetencyCategoryDto competencyCategoryDto = new CompetencyCategoryDto();
        competencyCategoryDto.setName("수정한 하위 역량");
        competencyCategoryDto.setDescription("수정한 하위 역량 설명");
        competencyCategoryDto.setLevelType("SUB_COMPETENCY");
        competencyCategoryDto.setParentId(1L);

        when(subCompetencyCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(existingSubCategory));

        // When: 수정 메서드 호출
        categoryService.updateCategory(subCategoryId, competencyCategoryDto);

        // Then: 필드 값이 DTO와 일치하게 변경되었는지 확인 및 저장 메서드 호출 검증
        assertEquals("수정한 하위 역량", existingSubCategory.getSubCategoryName());
        assertEquals("수정한 하위 역량 설명", existingSubCategory.getSubCategoryNote());
        verify(subCompetencyCategoryRepository, times(1)).save(existingSubCategory);
    }


    @Test
    @DisplayName("핵심역량 카테고리 삭제 테스트")
    void deleteCoreCategoryTest() {
        // Given: 존재하는 핵심역량 ID에 대한 Mock 객체 설정
        Long coreCategoryId = 1L;
        CoreCompetencyCategory existingCoreCategory = CoreCompetencyCategory.builder().id(coreCategoryId).build();
        when(coreCompetencyCategoryRepository.findById(coreCategoryId)).thenReturn(Optional.of(existingCoreCategory));

        // When: 핵심역량 삭제 메서드 실행
        categoryService.deleteCategory("CORE_COMPETENCY", coreCategoryId);

        // Then: 핵심역량이 정상적으로 삭제되었는지 확인
        verify(coreCompetencyCategoryRepository, times(1)).delete(existingCoreCategory);
    }

    @Test
    @DisplayName("하위역량 카테고리 삭제 테스트")
    void deleteSubCategoryTest() {
        // Given: 존재하는 하위역량 ID에 대한 Mock 객체 설정
        Long subCategoryId = 1L;
        SubCompetencyCategory existingSubCategory = SubCompetencyCategory.builder().id(subCategoryId).build();
        when(subCompetencyCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(existingSubCategory));

        // When: 하위역량 삭제 메서드 실행
        categoryService.deleteCategory("SUB_COMPETENCY", subCategoryId);

        // Then: 하위역량이 정상적으로 삭제되었는지 확인
        verify(subCompetencyCategoryRepository, times(1)).delete(existingSubCategory);
    }

    @Test
    @DisplayName("핵심역량 이름 중복 체크 테스트")
    void checkCoreCategoryNameDuplicateTest() {
        // Given: 중복된 핵심역량 이름에 대해 true 반환 설정
        String categoryName = "창의역량";
        when(coreCompetencyCategoryRepository.existsByCoreCategoryName(categoryName)).thenReturn(true);

        // When: 중복 여부 확인 메서드 실행
        boolean isDuplicate = categoryService.isCoreCategoryNameDuplicate(categoryName);

        // Then: true 반환 및 existsByCoreCategoryName 호출 확인
        assertTrue(isDuplicate);
        verify(coreCompetencyCategoryRepository, times(1)).existsByCoreCategoryName(categoryName);
    }

    @Test
    @DisplayName("하위역량 이름 중복 체크 테스트")
    void checkSubCategoryNameDuplicateTest() {
        // Given: coreCategoryId 하위에 동일한 이름의 하위역량이 있을 때
        Long coreCategoryId = 1L;
        String subCategoryName = "창의적 사고";
        when(subCompetencyCategoryRepository.findByCoreCategoryId(coreCategoryId))
                .thenReturn(List.of(SubCompetencyCategory.builder().subCategoryName(subCategoryName).build()));

        // When: 중복 여부 확인
        boolean isDuplicate = categoryService.isSubCategoryNameDuplicate(coreCategoryId, subCategoryName);

        // Then: true 반환 및 저장소 메서드 호출 확인
        assertTrue(isDuplicate);
        verify(subCompetencyCategoryRepository, times(1)).findByCoreCategoryId(coreCategoryId);
    }

    @Test
    @DisplayName("핵심역량 전체 조회 테스트")
    void getAllCoreCompetencyCategoriesTest() {
        // Given: 2개의 핵심역량이 저장되어 있을 때 반환 설정
        List<CoreCompetencyCategory> categories = List.of(
                CoreCompetencyCategory.builder().id(1L).coreCategoryName("창의역량").build(),
                CoreCompetencyCategory.builder().id(2L).coreCategoryName("비판적 사고").build()
        );
        when(coreCompetencyCategoryRepository.findAll()).thenReturn(categories);

        // When: 전체 조회 실행
        List<CoreCompetencyCategory> result = categoryService.getAllCoreCompetencyCategories();

        // Then: 크기 및 값 확인
        assertEquals(2, result.size());
        assertEquals("창의역량", result.get(0).getCoreCategoryName());
        verify(coreCompetencyCategoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("하위역량 전체 조회 테스트")
    void getAllSubCompetencyCategoriesTest() {
        // Given: 2개의 하위역량이 저장되어 있을 때 반환 설정
        List<SubCompetencyCategory> subCategories = List.of(
                SubCompetencyCategory.builder().id(1L).subCategoryName("창의적 사고").build(),
                SubCompetencyCategory.builder().id(2L).subCategoryName("비판적 사고").build()
        );
        when(subCompetencyCategoryRepository.findAll()).thenReturn(subCategories);

        // When: 전체 조회 실행
        List<SubCompetencyCategory> result = categoryService.getAllSubCompetencyCategories();

        // Then: 크기 및 첫 번째 값 확인
        assertEquals(2, result.size());
        assertEquals("창의적 사고", result.get(0).getSubCategoryName());
        verify(subCompetencyCategoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("핵심역량 상세 조회 테스트")
    void getCoreCategoryTest() {
        // Given: 특정 ID의 핵심역량 존재 시 반환 설정
        Long coreCategoryId = 1L;
        CoreCompetencyCategory coreCategory = CoreCompetencyCategory.builder().id(coreCategoryId).coreCategoryName("창의역량").build();
        when(coreCompetencyCategoryRepository.findById(coreCategoryId)).thenReturn(Optional.of(coreCategory));

        // When: 상세 조회 실행
        CoreCompetencyCategory result = categoryService.getCoreCategory(coreCategoryId);

        // Then: 결과가 null 아님 및 이름 일치 확인
        assertNotNull(result);
        assertEquals("창의역량", result.getCoreCategoryName());
        verify(coreCompetencyCategoryRepository, times(1)).findById(coreCategoryId);
    }

    @Test
    @DisplayName("하위역량 상세 조회 테스트")
    void getSubCategoryTest() {
        // Given: 특정 ID의 하위역량 존재 시 반환 설정
        Long subCategoryId = 1L;
        SubCompetencyCategory subCategory = SubCompetencyCategory.builder().id(subCategoryId).subCategoryName("창의적 사고").build();
        when(subCompetencyCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        // When: 상세 조회 실행
        SubCompetencyCategory result = categoryService.getSubCategory(subCategoryId);

        // Then: 결과가 null 아님 및 이름 일치 확인
        assertNotNull(result);
        assertEquals("창의적 사고", result.getSubCategoryName());
        verify(subCompetencyCategoryRepository, times(1)).findById(subCategoryId);
    }

}
