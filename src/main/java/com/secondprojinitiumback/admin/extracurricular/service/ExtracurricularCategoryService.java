package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtracurricularCategoryService {

    private final ExtracurricularCategoryRepository extracurricularCategoryRepository;
    private final ModelMapper modelMapper;

    public void insertExtracurricularCategory(ExtracurricularCategoryFormDTO dto) {
        // 카테고리 사용 여부 기본 값 설정
        dto.setCtgryUseYn("Y");
        // 데이터 생성일자 기본 값 설정
        dto.setDataCrtDt(java.sql.Date.valueOf(LocalDate.now()));
        // dto를 ExtracurricularCategory로 변환
        ExtracurricularCategory extracurricularCategory = modelMapper.map(dto, ExtracurricularCategory.class);
        // ExtracurricularCategory 저장
        extracurricularCategoryRepository.save(extracurricularCategory);
    }

    public void updateExtracurricularCategory(Long id, String ctgryUseYn) {
        // 아이디를 받아와 수정할 ExtracurricularCategory 객체를 조회
        ExtracurricularCategory category = extracurricularCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. id=" + id));
        // 상태 값 업데이트
        category.setCtgryUseYn(ctgryUseYn);
        // 변경된 값 저장
        extracurricularCategoryRepository.save(category);
    }

    public void deleteExtracurricularCategory(Long id) {
        // 아이디를 받아와 삭제할 ExtracurricularCategory 객체를 조회
        ExtracurricularCategory category = extracurricularCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. id=" + id));
        // ExtracurricularCategory 삭제
        extracurricularCategoryRepository.delete(category);
    }

   public List<ExtracurricularCategoryDTO> getExtracurricularCategoryList(Long stgrId) {
        // 상위 카테고리 번호를 받아와 ExtracurricularCategory 객체를 조회
        List<ExtracurricularCategory> categories = extracurricularCategoryRepository.findByStgrId(stgrId);
        // ExtracurricularCategory 객체들을 ExtracurricularCategoryDTO로 변환
        return categories.stream()
                .map(category -> modelMapper.map(category, ExtracurricularCategoryDTO.class))
                .toList();
    }
}
