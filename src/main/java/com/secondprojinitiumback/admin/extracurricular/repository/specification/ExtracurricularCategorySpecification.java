package com.secondprojinitiumback.admin.extracurricular.repository.specification;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExtracurricularCategorySpecification {

    public static Specification<ExtracurricularCategory> filterCategories(
            List<Integer> competencyIds,
            String programName,
            String departmentCode
    ) {
        return (Root<ExtracurricularCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 핵심역량 필터 (stgrId IN (:competencyIds))
            if (competencyIds != null && !competencyIds.isEmpty()) {
                CriteriaBuilder.In<Long> inClause = cb.in(root.get("stgrId"));
                for (Integer id : competencyIds) {
                    inClause.value(id.longValue());
                }
                predicates.add(inClause);
            }

            // 프로그램 분류명 LIKE 필터
            if (programName != null && !programName.isEmpty()) {
                predicates.add(cb.like(root.get("ctgryNm"), "%" + programName + "%"));
            }

            // 주관부서 코드 필터 (schoolSubject.subjectCode = :departmentCode)
            if (departmentCode != null && !departmentCode.isEmpty()) {
                Join<ExtracurricularCategory, ?> joinSubject = root.join("schoolSubject", JoinType.INNER);
                predicates.add(cb.equal(joinSubject.get("subjectCode"), departmentCode));
            }

            // 조건들을 AND로 결합
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
