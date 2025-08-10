package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResult;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface IdealTalentProfileRepository extends JpaRepository<IdealTalentProfile, Long> {
    @Query("SELECT i FROM IdealTalentProfile i " +
            "JOIN FETCH i.coreCompetencyCategories c")
    List<IdealTalentProfile> findAllWithCoreAndSub(); // 이름 그대로 둬도 됨



    interface CoreCompetencyResultRepository extends JpaRepository<CoreCompetencyResult,Long> {
    }
}
