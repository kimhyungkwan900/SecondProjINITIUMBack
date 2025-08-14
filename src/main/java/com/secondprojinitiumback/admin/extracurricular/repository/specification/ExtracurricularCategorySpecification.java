package com.secondprojinitiumback.admin.extracurricular.repository.specification;

import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ExtracurricularCategorySpecification {
    public static Specification<ExtracurricularCategory> filterCategories(
            Long coreId,
            String programName,
            String departmentCode,
            SubCompetencyCategoryRepository subCompetencyCategoryRepository
    ) {
        return (Root<ExtracurricularCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 핵심역량 필터 (stgrId IN (:competencyIds))
            if (coreId != null) { // 핵심역량 ID 하나
                // 1. 해당 핵심역량의 하위역량 ID 목록 조회
                List<Long> subIds = subCompetencyCategoryRepository
                        .findByCoreCompetencyCategoryId(coreId)
                        .stream()
                        .map(o -> ((SubCompetencyCategory) o).getId())
                        .collect(Collectors.toList());

                if (!subIds.isEmpty()) {
                    // 2. 하위역량 ID로 ExtracurricularCategory 필터
                    CriteriaBuilder.In<Long> inClause = cb.in(root.get("stgrId"));
                    for (Long subId : subIds) {
                        inClause.value(subId);
                    }
                    predicates.add(inClause);
                } else {
                    // 하위역량이 없으면 아예 빈 결과를 위해 false 추가
                    predicates.add(cb.disjunction());
                }
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
