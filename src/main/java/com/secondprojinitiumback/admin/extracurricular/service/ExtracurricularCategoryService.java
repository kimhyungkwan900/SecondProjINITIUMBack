package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularCategoryRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.specification.ExtracurricularCategorySpecification;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtracurricularCategoryService {

    private final ExtracurricularCategoryRepository extracurricularCategoryRepository;
    private final ModelMapper modelMapper;
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final EmployeeRepository employeeRepository;

    // 비교과 카테고리 등록
    public void insertExtracurricularCategory(ExtracurricularCategoryFormDTO dto) {
        // 카테고리 사용 여부 기본 값 설정
        dto.setCtgryUseYn("Y");
        // 데이터 생성일자 기본 값 설정
        dto.setDataCrtDt(LocalDateTime.now());
        // dto를 ExtracurricularCategory로 변환
        ExtracurricularCategory extracurricularCategory = modelMapper.map(dto, ExtracurricularCategory.class);

        SchoolSubject schoolSubject = schoolSubjectRepository.findBySubjectCode(dto.getSubjectCode()).orElseThrow();
        if (schoolSubject == null) {
            throw new RuntimeException("주관부서(SubjectCode)가 올바르지 않습니다: " + dto.getSubjectCode());
        }

        extracurricularCategory.setSchoolSubject(schoolSubject);
        // ExtracurricularCategory 저장
        extracurricularCategoryRepository.save(extracurricularCategory);
    }

    // 비교과 카테고리 사용 수정
    public void updateExtracurricularCategory(ExtracurricularCategoryFormDTO dto) {
        ExtracurricularCategory category = extracurricularCategoryRepository.findById(dto.getCtgryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        // DTO에서 받은 값을 엔티티에 반영
        category.setStgrId(dto.getStgrId());
        category.setSchoolSubject(schoolSubjectRepository.findBySubjectCode(dto.getSubjectCode()).orElseThrow());
        category.setCtgryNm(dto.getCtgryNm());
        category.setCtgryDtl(dto.getCtgryDtl());
        category.setCtgryUseYn(dto.getCtgryUseYn());

        extracurricularCategoryRepository.save(category);
    }

    // 비교과 카테고리 삭제
    public void deleteExtracurricularCategory(Long id) {
        // 아이디를 받아와 삭제할 ExtracurricularCategory 객체를 조회
        ExtracurricularCategory category = extracurricularCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. id=" + id));
        // ExtracurricularCategory 삭제
        extracurricularCategoryRepository.delete(category);
    }

    // 비교과 카테고리 목록 조회
    @Transactional
    public List<ExtracurricularCategoryDTO> getExtracurricularCategoryList(Long stgrId) {
        List<ExtracurricularCategory> categories = extracurricularCategoryRepository.findByStgrId(stgrId, Sort.by(Sort.Direction.DESC, "ctgryId"));

        categories.forEach(c -> {
            System.out.println("ctgryId: " + c.getCtgryId());
            System.out.println("schoolSubject is null? " + (c.getSchoolSubject() == null));
            if (c.getSchoolSubject() != null) {
                System.out.println("subjectCode: " + c.getSchoolSubject().getSubjectCode());
                System.out.println("subjectName: " + c.getSchoolSubject().getSubjectName());
            }
        });

        return categories.stream()
                .map(category -> {
                    ExtracurricularCategoryDTO dto = modelMapper.map(category, ExtracurricularCategoryDTO.class);
                    if (category.getSchoolSubject() != null) {
                        dto.setSubjectCode(category.getSchoolSubject().getSubjectCode());
                        dto.setSubjectName(category.getSchoolSubject().getSubjectName());
                    }
                    return dto;
                })
                .toList();
    }

    public List<ExtracurricularCategoryDTO> findByCategoryId(Long categoryId) {
        List<ExtracurricularCategory> entities = extracurricularCategoryRepository.findByStgrId(categoryId,Sort.by(Sort.Direction.DESC, "ctgryId"));
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ExtracurricularCategoryDTO> findByFilters(String programName, List<Integer> competencyIds, String departmentCode) {
        if (competencyIds != null && competencyIds.isEmpty()) {
            competencyIds = null;  // 빈 리스트일 땐 null로 변경
        }
        Specification<ExtracurricularCategory> spec = ExtracurricularCategorySpecification.filterCategories(
                competencyIds,
                programName,
                departmentCode
        );
        List<ExtracurricularCategory> entities = extracurricularCategoryRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "ctgryId"));
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ExtracurricularCategoryDTO toDto(ExtracurricularCategory entity) {
        return ExtracurricularCategoryDTO.builder()
                .ctgryId(entity.getCtgryId())
                .stgrId(entity.getStgrId())
                .ctgryNm(entity.getCtgryNm())
                .ctgryDtl(entity.getCtgryDtl())  // 추가
                .ctgryUseYn(entity.getCtgryUseYn())
                .subjectCode(entity.getSchoolSubject() != null ? entity.getSchoolSubject().getSubjectCode() : null)
                .subjectName(entity.getSchoolSubject() != null ? entity.getSchoolSubject().getSubjectName() : null)
                .build();
    }


    // 학과 가져오기
    public List<SchoolSubject> findAllSchoolSubject() {
        return schoolSubjectRepository.findAll();
    }

    // 로그인한 학과가 관리하는 분류체계 불러오기
    public List<ExtracurricularCategoryDTO> findByEmpNo(String empNo) {
        Employee employee = employeeRepository.findById(empNo).orElseThrow();
        List<ExtracurricularCategory> categoryList = extracurricularCategoryRepository
                .findExtracurricularCategoriesBySchoolSubject_SubjectCode(employee.getSchoolSubject().getSubjectCode());
        return categoryList.stream()
                .filter(category -> "Y".equalsIgnoreCase(category.getCtgryUseYn()))  // useYn이 Y인 항목만 필터링
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
