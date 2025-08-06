package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResult;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface IdealTalentProfileRepository extends JpaRepository<IdealTalentProfile, Long> {
    @Query("SELECT p FROM IdealTalentProfile p " +
            "JOIN FETCH p.coreCompetencyCategories c " +
            "JOIN FETCH c.subCompetencyCategories")
    List<IdealTalentProfile> findAllWithCoreAndSub();


    interface CoreCompetencyResultRepository extends JpaRepository<CoreCompetencyResult,Long> {
    }
}
