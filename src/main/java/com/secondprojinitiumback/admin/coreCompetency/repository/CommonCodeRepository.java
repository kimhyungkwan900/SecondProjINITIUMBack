package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.common.domain.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {
    Optional<CommonCode> findByCodeAndGroup(String semesterCode, String semester);

    Optional<CommonCode> findByCdAndCdSe(String cd, String cdSe);
}
