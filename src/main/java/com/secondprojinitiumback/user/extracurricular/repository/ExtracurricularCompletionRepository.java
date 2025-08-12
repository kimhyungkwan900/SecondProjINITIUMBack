
package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtracurricularCompletionRepository extends JpaRepository<ExtracurricularCompletion,Long> {
    boolean existsByExtracurricularApply(ExtracurricularApply apply);
    Optional<ExtracurricularCompletion> findExtracurricularCompletionByExtracurricularApply_EduAplyId(Long eduAplyId);

//    ExtracurricularCompletion findExtracurricularCompletionByExtracurricularApply_EduAplyId(Long extracurricularApplyEduAplyId);
}
