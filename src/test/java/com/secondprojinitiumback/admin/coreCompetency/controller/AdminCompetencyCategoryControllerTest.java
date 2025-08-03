package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminCompetencyCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("핵심역량 등록 테스트")
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
    }

    @Test
    @DisplayName("하위역량 등록 테스트")
    void createSubCategory() throws Exception {
        CompetencyCategoryDto dto = new CompetencyCategoryDto();
        dto.setName("창의적 사고");
        dto.setDescription("창의적 문제 해결 능력 향상");
        dto.setLevelType("SUB_COMPETENCY");
        dto.setParentId(1L);

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
        dto.setLevelType("CORE_COMPETENCY");
        dto.setIdealTalentProfileId(1L);

        mockMvc.perform(post("/api/admin/competencyCategory/update/1")
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
        dto.setLevelType("SUB_COMPETENCY");
        dto.setParentId(1L);

        mockMvc.perform(post("/api/admin/competencyCategory/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("수정 완료"));
    }

    @Test
    @DisplayName("핵심역량 삭제 테스트")
    void deleteCoreCategory() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/delete/CORE_COMPETENCY/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 완료"));
    }

    @Test
    @DisplayName("하위역량 삭제 테스트")
    void deleteSubCategory() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/delete/SUB_COMPETENCY/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 완료"));
    }

    @Test
    @DisplayName("핵심역량 전체 조회 테스트")
    void getAllCore() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/core"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("하위역량 전체 조회 테스트")
    void getAllSub() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/sub"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("핵심역량 단건 조회 테스트")
    void getCore() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/core/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("하위역량 단건 조회 테스트")
    void getSub() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/sub/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("핵심역량 중복 체크 테스트")
    void checkCoreDuplicate() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/check/core")
                        .param("name", "창의역량"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("하위역량 중복 체크 테스트")
    void checkSubDuplicate() throws Exception {
        mockMvc.perform(post("/api/admin/competencyCategory/check/sub")
                        .param("name", "창의적 사고")
                        .param("parentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
