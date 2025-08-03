package com.secondprojinitiumback.common.repository;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.CommonCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {
    Optional<CommonCode> findByCodeAndGroup(String semesterCode, String semester);
}
