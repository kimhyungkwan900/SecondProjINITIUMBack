package com.secondprojinitiumback.admin.extracurricular.repository.specification;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExtracurricularProgramSpecification {
    public static Specification<ExtracurricularProgram> filterBy(ProgramFilterRequest filter){
        return(root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("sttsNm"), filter.getStatus()));
            }

            if (filter.getProgramName() != null && !filter.getProgramName().isBlank()) {
                predicates.add(cb.like(root.get("eduNm"), "%" + filter.getProgramName() + "%"));
            }

            if (filter.getDepartmentCode() != null && !filter.getDepartmentCode().isBlank()) {
                predicates.add(cb.equal(root.get("employee").get("schoolSubject").get("subjectCode"), filter.getDepartmentCode()));
            }

            if (filter.getEduType() != null) {
                predicates.add(cb.equal(root.get("eduType"), filter.getEduType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
