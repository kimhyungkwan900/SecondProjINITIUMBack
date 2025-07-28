package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtracurricularCategoryRepository extends JpaRepository<ExtracurricularCategory, Long> {
    public List<ExtracurricularCategory> findByStgrId(Long stgrId);
}
