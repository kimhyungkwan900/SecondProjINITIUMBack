package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCompetencyCategoryService;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.CommonCodeId;
import com.secondprojinitiumback.common.security.config.jwt.TokenAuthenticationFilter;
import com.secondprojinitiumback.common.security.config.jwt.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * AdminCompetencyCategoryController에 대한 단위 테스트
 * 핵심역량/하위역량 등록, 수정, 삭제, 조회, 중복체크 API의 동작을 검증
 */
@WebMvcTest(controllers = AdminCompetencyCategoryController.class)
@AutoConfigureMockMvc(addFilters = false) // Spring Security 필터 비활성화
class AdminCompetencyCategoryControllerTest {

    @MockBean
    private TokenProvider tokenProvider; // JWT 인증 모의 객체

    @MockBean
    private TokenAuthenticationFilter tokenAuthenticationFilter; // 시큐리티 필터 비활성화를 위한 모의 객체

    @Autowired
    private MockMvc mockMvc; // HTTP 요청을 모의로 수행할 수 있는 객체

    @Autowired
    private ObjectMapper objectMapper; // 객체 <-> JSON 변환용

    @MockBean
    private AdminCompetencyCategoryService categoryService; // 서비스 계층을 모의(mock) 처리

    @Test
    @DisplayName("핵심역량 등록 테스트")
    void createCoreCategory() throws Exception {
        // given
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setName("창의역량");
        dto.setDescription("창의적 문제해결 능력");
        dto.setIdealTalentProfileId(1L);

        CommonCodeId codeId = new CommonCodeId("C", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);

        // when
        doNothing().when(categoryService).createCategory(dto);

        // then
        mockMvc.perform(post("/api/admin/competencyCategory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("등록 완료"));
    }

    @Test
    @DisplayName("하위역량 등록 테스트")
    void createSubCategory() throws Exception {
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setName("창의적 사고");
        dto.setDescription("창의적 문제 해결 능력 향상");
        dto.setParentId(1L);

        CommonCodeId codeId = new CommonCodeId("S", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);

        doNothing().when(categoryService).createCategory(dto);

        mockMvc.perform(post("/api/admin/competencyCategory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("등록 완료"));
    }

    @Test
    @DisplayName("핵심역량 수정 테스트")
    void updateCoreCategory() throws Exception {
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setId(1L);
        dto.setName("수정한 핵심역량");
        dto.setDescription("수정한 핵심역량 설명");
        dto.setIdealTalentProfileId(1L);

        CommonCodeId codeId = new CommonCodeId("C", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);

        doNothing().when(categoryService).updateCategory(1L, dto);

        mockMvc.perform(put("/api/admin/competencyCategory/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("수정 완료"));
    }

    @Test
    @DisplayName("하위역량 수정 테스트")
    void updateSubCategory() throws Exception {
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setId(1L);
        dto.setName("하위역량 수정");
        dto.setDescription("하위역량 설명 수정");
        dto.setParentId(1L);

        CommonCodeId codeId = new CommonCodeId("S", "COMP");
        CommonCode commonCode = CommonCode.builder().id(codeId).build();
        dto.setCompetencyCategory(commonCode);

        doNothing().when(categoryService).updateCategory(1L, dto);

        mockMvc.perform(put("/api/admin/competencyCategory/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("수정 완료"));
    }

    @Test
    @DisplayName("핵심역량 삭제 테스트")
    void deleteCoreCategory() throws Exception {
        Long id = 1L;

        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setCompetencyCategory(
                CommonCode.builder()
                        .id(new CommonCodeId("C", "COMP"))
                        .codeName("핵심역량")
                        .build()
        );

        doNothing().when(categoryService).deleteCategory(eq(id), any(CompetencyCategoryDto.class));

        mockMvc.perform(delete("/api/admin/competencyCategory/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 완료"));

        verify(categoryService, times(1)).deleteCategory(eq(id), any(CompetencyCategoryDto.class));
    }

    @Test
    @DisplayName("하위역량 삭제 테스트")
    void deleteSubCategory() throws Exception {
        Long id = 1L;

        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setCompetencyCategory(
                CommonCode.builder()
                        .id(new CommonCodeId("S", "COMP"))
                        .codeName("하위역량")
                        .build()
        );

        doNothing().when(categoryService).deleteCategory(eq(id), any(CompetencyCategoryDto.class));

        mockMvc.perform(delete("/api/admin/competencyCategory/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 완료"));

        verify(categoryService, times(1)).deleteCategory(eq(id), any(CompetencyCategoryDto.class));
    }

    @Test
    @DisplayName("핵심역량 전체 조회 테스트")
    void getAllCore() throws Exception {
        when(categoryService.getAllCoreCompetencyCategories()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/admin/competencyCategory/core"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("하위역량 전체 조회 테스트")
    void getAllSub() throws Exception {
        when(categoryService.getAllSubCompetencyCategories()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/admin/competencyCategory/sub"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("핵심역량 단건 조회 테스트")
    void getCore() throws Exception {
        when(categoryService.getCoreCategory(1L)).thenReturn(new CoreCompetencyCategory());

        mockMvc.perform(get("/api/admin/competencyCategory/core/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("하위역량 단건 조회 테스트")
    void getSub() throws Exception {
        when(categoryService.getSubCategory(1L)).thenReturn(new SubCompetencyCategory());

        mockMvc.perform(get("/api/admin/competencyCategory/sub/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("핵심역량 중복 체크 테스트")
    void checkCoreDuplicate() throws Exception {
        when(categoryService.isCoreCategoryNameDuplicate("창의역량")).thenReturn(true);

        mockMvc.perform(get("/api/admin/competencyCategory/check/core")
                        .param("name", "창의역량"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("하위역량 중복 체크 테스트")
    void checkSubDuplicate() throws Exception {
        when(categoryService.isSubCategoryNameDuplicate(1L, "창의적 사고")).thenReturn(true);

        mockMvc.perform(get("/api/admin/competencyCategory/check/sub")
                        .param("name", "창의적 사고")
                        .param("coreId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
