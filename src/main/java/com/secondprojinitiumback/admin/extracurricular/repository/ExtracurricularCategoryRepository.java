package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtracurricularCategoryRepository extends JpaRepository<ExtracurricularCategory, Long>,
        JpaSpecificationExecutor<ExtracurricularCategory> {
    // 상위 카테고리 번호로 비교과 카테고리 목록 조회
    List<ExtracurricularCategory> findByStgrId(Long stgrId, Sort sort);


}
