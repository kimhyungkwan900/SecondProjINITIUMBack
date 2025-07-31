package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtracurricularCompletionRepository extends JpaRepository<ExtracurricularCompletion,Long> {
    boolean existsByExtracurricularApply(ExtracurricularApply apply);
}
