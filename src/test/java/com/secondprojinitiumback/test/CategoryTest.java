package com.secondprojinitiumback.test;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularCategoryRepository;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryTest {

    @Autowired
    private ExtracurricularCategoryService extracurricularCategoryService;

    @Autowired
    private ExtracurricularCategoryRepository extracurricularCategoryRepository;

    @Test
    @DisplayName("카테고리 등록 테스트")
    public void testCategoryInsert() {
        ExtracurricularCategoryFormDTO dto = new ExtracurricularCategoryFormDTO();
        dto.setStgrId(11L);
        dto.setCtgryNm("Test Category3");
        extracurricularCategoryService.insertExtracurricularCategory(dto);
    }


    @Test
    @DisplayName("카테고리 조회 테스트")
    public void testCategoryList() {
        // given
        Long stgrId = 11L;
        // when
        List<ExtracurricularCategoryDTO> result = extracurricularCategoryService.getExtracurricularCategoryList(stgrId);
        // then
        assertNotNull(result); // 결과가 null이 아닌지
        assertFalse(result.isEmpty(), "조회된 카테고리 리스트가 비어 있지 않아야 합니다.");
        // 첫 번째 결과 출력(확인용)
        result.forEach(dto -> System.out.println("카테고리 ID: " + dto.getCtgryId() + ", 이름: " + dto.getCtgryNm()));
    }
//    @Test
//    @DisplayName("카테고리 삭제 테스트")
//    public void testCategoryDelete(){
//        extracurricularCategoryService.deleteExtracurricularCategory(7L);
//    }

}
