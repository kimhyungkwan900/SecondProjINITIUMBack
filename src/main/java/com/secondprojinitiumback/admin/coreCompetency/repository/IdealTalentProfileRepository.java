package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResult;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdealTalentProfileRepository extends JpaRepository<IdealTalentProfile, Long> {
    interface CoreCompetencyResultRepository extends JpaRepository<CoreCompetencyResult,Long> {
    }
}
