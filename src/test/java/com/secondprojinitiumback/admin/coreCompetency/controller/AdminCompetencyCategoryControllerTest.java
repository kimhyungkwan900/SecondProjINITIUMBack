package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCompetencyCategoryService;
import com.secondprojinitiumback.user.coreCompetency.dto.CoreCompetencyCategoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminCompetencyCategoryController.class)
class AdminCompetencyCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminCompetencyCategoryService adminCompetencyCategoryService;

    @Test
    @DisplayName("역량 등록 API 호출 테스트")
    void createCoreCategory() throws Exception {
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setName("창의역량");
        dto.setDescription("창의적 문제해결 능력");
        dto.setLevelType("CORE_COMPETENCY");
        dto.setIdealTalentProfileId(1L);

        mockMvc.perform(post("/api/admin/competencyCategory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("등록 완료"));

        verify(adminCompetencyCategoryService, times(1)).createCategory(any());

        CompetencyCategoryDto competencyCategoryDto = new CompetencyCategoryDto();
        competencyCategoryDto.setName("창의적 사고");
        competencyCategoryDto.setDescription("창의적 문제 해결 능력 향상");
        competencyCategoryDto.setLevelType("SUB_COMPETENCY");
        competencyCategoryDto.setParentId(1L);

        mockMvc.perform(post("/api/admin/competencyCategory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competencyCategoryDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("등록 완료"));

        verify(adminCompetencyCategoryService, times(1)).createCategory(any());
    }

    @Test
    @DisplayName("역량 수정 API 호출 테스트")
    void updateCoreCategory() throws Exception {
        CompetencyCategoryDto dto1 = new CompetencyCategoryDto();
        dto1.setId(1L);
        dto1.setName("수정한 핵심역량");
        dto1.setDescription("수정한 핵심역량 설명");
        dto1.setLevelType("CORE_COMPETENCY");
        dto1.setIdealTalentProfileId(1L);

        mockMvc.perform(post("/api/admin/competencyCategory/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isOk())
                .andExpect(content().string("수정 완료"));

        verify(adminCompetencyCategoryService, times(1)).updateCategory(any(), any());

        CompetencyCategoryDto dto2 = new CompetencyCategoryDto();
        dto2.setId(1L);
        dto2.setName("하위역량 수정");
        dto2.setDescription("하위역량 설명 수정");
        dto2.setLevelType("SUB_COMPETENCY");
        dto2.setParentId(1L);

        mockMvc.perform(post("/api/admin/competencyCategory/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto2)))
                .andExpect(status().isOk())
                .andExpect(content().string("수정 완료"));

        verify(adminCompetencyCategoryService, times(1)).updateCategory(any(), any());

    }

    @Test
    @DisplayName("역량 삭제 API 호출 테스트")
    void deleteCoreCategory() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/delete/CORE_COMPETENCY/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 완료"));

        verify(adminCompetencyCategoryService, times(1)).deleteCategory("CORE_COMPETENCY", 1L);

        mockMvc.perform(post("/api/admin/competencyCategory/delete/SUB_COMPETENCY/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 완료"));

        verify(adminCompetencyCategoryService, times(1)).deleteCategory("SUB_COMPETENCY", 1L);
    }

    @Test
    @DisplayName("핵심역량 카테고리 전체 조회 API 호출 테스트")
    void getAllCore() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/core"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(adminCompetencyCategoryService, times(1)).getAllCoreCompetencyCategories();
    }

    @Test
    @DisplayName("하위역량 카테고리 전체 조회 API 호출 테스트")
    void getAllSub() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/sub"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(adminCompetencyCategoryService, times(1)).getAllSubCompetencyCategories();
    }

    @Test
    @DisplayName("핵심역량 카테고리 단일 조회 API 호출 테스트")
    void getCore() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/core/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(adminCompetencyCategoryService, times(1)).getCoreCategory(1L);
    }

    @Test
    @DisplayName("하위역량 카테고리 단일 조회 API 호출 테스트")
    void getSub() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/sub/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(adminCompetencyCategoryService, times(1)).getSubCategory(1L);
    }

    @Test
    @DisplayName("핵심역량 이름 중복 여부 확인 API 호출 테스트")
    void checkCoreDuplicate() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/check/core")
                        .param("name", "창의역량"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(adminCompetencyCategoryService, times(1)).isCoreCategoryNameDuplicate("창의역량");
    }

    @Test
    @DisplayName("하위역량 이름 중복 여부 확인 API 호출 테스트")
    void checkSubDuplicate() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/check/sub")
                        .param("name", "창의적 사고"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(adminCompetencyCategoryService, times(1)).isSubCategoryNameDuplicate(1L,"창의적 사고");
    }



}