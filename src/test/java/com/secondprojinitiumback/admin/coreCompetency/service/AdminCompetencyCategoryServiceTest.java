package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.CommonCodeId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

// AdminCompetencyCategoryService의 단위 테스트 클래스
// 핵심역량/하위역량 등록, 수정, 삭제, 조회, 중복 체크 등 서비스 메서드 테스트
class AdminCompetencyCategoryServiceTest {

    // 핵심역량 레포지토리 모의 객체
    @Mock
    private CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;

    // 인재상 레포지토리 모의 객체
    @Mock
    private IdealTalentProfileRepository idealTalentProfileRepository;

    // 하위역량 레포지토리 모의 객체
    @Mock
    private SubCompetencyCategoryRepository subCompetencyCategoryRepository;

    // 테스트 대상 서비스에 모의 객체 주입
    @InjectMocks
    private AdminCompetencyCategoryService categoryService;

    @Test
    @DisplayName("핵심역량 카테고리 등록 테스트")
    void createCategoryTest() {
        // 핵심역량 등록을 위한 DTO와 인재상 엔티티 설정
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setId(1L);
        dto.setName("창의역량");
        dto.setDescription("창의적 문제 해결 능력");
        dto.setIdealTalentProfileId(1L);

        CommonCodeId codeId = new CommonCodeId("C", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);


        // 인재상 엔티티가 정상적으로 조회됨을 모의
        IdealTalentProfile profile = IdealTalentProfile.builder().id(1L).build();
        when(idealTalentProfileRepository.findById(1L)).thenReturn(Optional.of(profile));

        // createCategory 호출 시 save가 정상적으로 호출됨을 검증
        categoryService.createCategory(dto);

        // 핵심역량 저장이 정확히 1번 호출되었는지 검증
        verify(coreCompetencyCategoryRepository, times(1)).save(any(CoreCompetencyCategory.class));
    }

    @Test
    @DisplayName("하위역량 카테고리 등록 테스트")
    void createSubCategoryTest() {
        // 하위역량 등록용 DTO와 상위 카테고리 설정
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setId(1L);
        dto.setName("창의적 사고");
        dto.setDescription("창의적 문제 해결 능력 향상");
        dto.setParentId(1L);

        CommonCodeId codeId = new CommonCodeId("S", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);

        // 상위 카테고리 존재 모의
        CoreCompetencyCategory parent = CoreCompetencyCategory.builder().id(1L).build();
        when(coreCompetencyCategoryRepository.findById(1L)).thenReturn(Optional.of(parent));

        categoryService.createCategory(dto);

        // 하위역량 저장이 정확히 1회 호출되었는지 검증
        verify(subCompetencyCategoryRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("핵심역량 카테고리 수정 테스트")
    void updateCategoryTest() {
        Long id = 1L;

        // 기존 핵심역량 객체 설정
        CoreCompetencyCategory existing = CoreCompetencyCategory.builder().id(id).build();
        IdealTalentProfile newProfile = IdealTalentProfile.builder().id(10L).build();

        // 수정 요청 DTO 구성
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setName("수정한 역량");
        dto.setDescription("수정한 역량 설명");
        dto.setIdealTalentProfileId(1L);

        CommonCodeId codeId = new CommonCodeId("C", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);

        // 레포지토리 응답 모의
        when(coreCompetencyCategoryRepository.findById(id)).thenReturn(Optional.of(existing));
        when(idealTalentProfileRepository.findById(1L)).thenReturn(Optional.of(newProfile));

        categoryService.updateCategory(id, dto);

        // 필드 수정 검증
        assertEquals("수정한 역량", existing.getCoreCategoryName());
        assertEquals("수정한 역량 설명", existing.getCoreCategoryNote());

        verify(coreCompetencyCategoryRepository, times(1)).save(existing);
    }

    @Test
    @DisplayName("하위역량 카테고리 수정 테스트")
    void updateSubCategoryTest() {
        Long id = 1L;

        SubCompetencyCategory existing = SubCompetencyCategory.builder().id(id).build();
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setName("수정한 하위 역량");
        dto.setDescription("수정한 하위 역량 설명");
        dto.setParentId(1L);

        CommonCodeId codeId = new CommonCodeId("S", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);

        when(subCompetencyCategoryRepository.findById(id)).thenReturn(Optional.of(existing));

        categoryService.updateCategory(id, dto);

        assertEquals("수정한 하위 역량", existing.getSubCategoryName());
        assertEquals("수정한 하위 역량 설명", existing.getSubCategoryNote());

        verify(subCompetencyCategoryRepository, times(1)).save(existing);
    }

    @Test
    @DisplayName("핵심역량 카테고리 삭제 테스트")
    void deleteCoreCategoryTest() {
        Long id = 1L;
        CoreCompetencyCategory core = CoreCompetencyCategory.builder().id(id).build();
        when(coreCompetencyCategoryRepository.findById(id)).thenReturn(Optional.of(core));

        categoryService.deleteCategory("CORE_COMPETENCY", id);

        verify(coreCompetencyCategoryRepository, times(1)).delete(core);
    }

    @Test
    @DisplayName("하위역량 카테고리 삭제 테스트")
    void deleteSubCategoryTest() {
        Long id = 1L;
        SubCompetencyCategory sub = SubCompetencyCategory.builder().id(id).build();
        when(subCompetencyCategoryRepository.findById(id)).thenReturn(Optional.of(sub));

        categoryService.deleteCategory("SUB_COMPETENCY", id);

        verify(subCompetencyCategoryRepository, times(1)).delete(sub);
    }

    @Test
    @DisplayName("핵심역량 이름 중복 체크 테스트")
    void checkCoreCategoryNameDuplicateTest() {
        String name = "창의역량";
        when(coreCompetencyCategoryRepository.existsByCoreCategoryName(name)).thenReturn(true);

        boolean result = categoryService.isCoreCategoryNameDuplicate(name);

        assertTrue(result);
        verify(coreCompetencyCategoryRepository, times(1)).existsByCoreCategoryName(name);
    }

    @Test
    @DisplayName("하위역량 이름 중복 체크 테스트")
    void checkSubCategoryNameDuplicateTest() {
        Long coreId = 1L;
        String name = "창의적 사고";
        SubCompetencyCategory sub = SubCompetencyCategory.builder().subCategoryName(name).build();
        when(subCompetencyCategoryRepository.findByCoreCompetencyCategory_Id(coreId)).thenReturn(List.of(sub));

        boolean result = categoryService.isSubCategoryNameDuplicate(coreId, name);

        assertTrue(result);
        verify(subCompetencyCategoryRepository, times(1)).findByCoreCompetencyCategory_Id(coreId);
    }

    @Test
    @DisplayName("핵심역량 전체 조회 테스트")
    void getAllCoreCompetencyCategoriesTest() {
        List<CoreCompetencyCategory> list = List.of(
                CoreCompetencyCategory.builder().id(1L).coreCategoryName("창의역량").build(),
                CoreCompetencyCategory.builder().id(2L).coreCategoryName("비판적 사고").build()
        );
        when(coreCompetencyCategoryRepository.findAll()).thenReturn(list);

        List<CoreCompetencyCategory> result = categoryService.getAllCoreCompetencyCategories();

        assertEquals(2, result.size());
        assertEquals("창의역량", result.get(0).getCoreCategoryName());
        verify(coreCompetencyCategoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("하위역량 전체 조회 테스트")
    void getAllSubCompetencyCategoriesTest() {
        List<SubCompetencyCategory> list = List.of(
                SubCompetencyCategory.builder().id(1L).subCategoryName("창의적 사고").build(),
                SubCompetencyCategory.builder().id(2L).subCategoryName("비판적 사고").build()
        );
        when(subCompetencyCategoryRepository.findAll()).thenReturn(list);

        List<SubCompetencyCategory> result = categoryService.getAllSubCompetencyCategories();

        assertEquals(2, result.size());
        assertEquals("창의적 사고", result.get(0).getSubCategoryName());
        verify(subCompetencyCategoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("핵심역량 단건 조회 테스트")
    void getCoreCategoryTest() {
        Long id = 1L;
        CoreCompetencyCategory core = CoreCompetencyCategory.builder().id(id).coreCategoryName("창의역량").build();
        when(coreCompetencyCategoryRepository.findById(id)).thenReturn(Optional.of(core));

        CoreCompetencyCategory result = categoryService.getCoreCategory(id);

        assertNotNull(result);
        assertEquals("창의역량", result.getCoreCategoryName());
        verify(coreCompetencyCategoryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("하위역량 단건 조회 테스트")
    void getSubCategoryTest() {
        Long id = 1L;
        SubCompetencyCategory sub = SubCompetencyCategory.builder().id(id).subCategoryName("창의적 사고").build();
        when(subCompetencyCategoryRepository.findById(id)).thenReturn(Optional.of(sub));

        SubCompetencyCategory result = categoryService.getSubCategory(id);

        assertNotNull(result);
        assertEquals("창의적 사고", result.getSubCategoryName());
        verify(subCompetencyCategoryRepository, times(1)).findById(id);
    }
}

