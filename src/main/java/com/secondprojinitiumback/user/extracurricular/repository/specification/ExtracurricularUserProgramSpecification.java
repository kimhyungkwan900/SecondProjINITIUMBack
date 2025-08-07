package com.secondprojinitiumback.user.extracurricular.repository.specification;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ExtracurricularUserProgramSpecification {
    public static Specification<ExtracurricularProgram> hasAnyCompetencyId(List<Integer> ids) {
        return (root, query, cb) -> {
            Join<ExtracurricularProgram, ExtracurricularCategory> categoryJoin = root.join("extracurricularCategory");
            return categoryJoin.get("stgrId").in(ids);
        };
    }

    public static Specification<ExtracurricularProgram> sttsNmNotRequested() {
        return (root, query, builder) -> builder.notEqual(root.get("sttsNm"), SttsNm.REQUESTED);
    }

    public static Specification<ExtracurricularProgram> hasKeyword(String keyword) {
        return (root, query, db) ->
                db.like(root.get("eduNm"), "%" + keyword + "%");
    }
}
