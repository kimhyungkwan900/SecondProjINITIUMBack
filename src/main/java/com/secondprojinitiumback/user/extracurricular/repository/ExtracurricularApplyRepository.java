package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtracurricularApplyRepository extends JpaRepository<ExtracurricularApply,Long> {

}
