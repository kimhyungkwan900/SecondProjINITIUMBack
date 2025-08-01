package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
class AdminCompetencyCategoryServiceTest {


    @Mock
    private CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;

    @Mock
    private IdealTalentProfileRepository idealTalentProfileRepository;

    @Mock
    private SubCompetencyCategoryRepository subCompetencyCategoryRepository;

    @InjectMocks
    private AdminCompetencyCategoryService categoryService;

    public void mockData_Sub(){
        CompetencyCategoryDto competencyCategoryDto2 = new CompetencyCategoryDto();
        competencyCategoryDto2.setId(1L); // 수정 시 사용
        competencyCategoryDto2.setName("창의적 사고");
        competencyCategoryDto2.setDescription("창의적 문제 해결 능력 향상");
        competencyCategoryDto2.setLevelType("SUB_COMPETENCY"); // 하위역량으로 설정
        competencyCategoryDto2.setParentId(1L); // 상위 카테고리 ID 설정
    }

    @Test
    @DisplayName("핵심역량 카테고리 등록 테스트")
    void createCategoryTest() {
        // Given
        // 필요한 Mock 객체와 데이터 설정
        // AdminCompetencyCategoryService 인스턴스 생성

        CompetencyCategoryDto competencyCategoryDto = new CompetencyCategoryDto();
        competencyCategoryDto.setName("창의역량");
        competencyCategoryDto.setDescription("창의적 문제 해결 능력");
        competencyCategoryDto.setLevelType("CORE_COMPETENCY");  // 핵심역량으로 설정
        competencyCategoryDto.setIdealTalentProfileId(1L); // 예시 인재상 ID

        IdealTalentProfile idealTalentProfile = IdealTalentProfile.builder().id(1L).build();
        when(idealTalentProfileRepository.findById(1L)).thenReturn(Optional.of(idealTalentProfile));

        // When
        // AdminCompetencyCategoryService의 createCategory 메서드 호출
        categoryService.createCategory(competencyCategoryDto);

        // Then
        // 결과 검증
        verify(coreCompetencyCategoryRepository, times(1)).save(any(CoreCompetencyCategory.class));
    }

}