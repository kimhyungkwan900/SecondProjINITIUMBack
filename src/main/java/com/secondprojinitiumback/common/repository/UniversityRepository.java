package com.secondprojinitiumback.common.repository;

import com.secondprojinitiumback.common.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, String> {

    // 이름만 조회 (코드→이름 매핑)
    @Query("select u.universityName from University u where u.universityCode = :code")
    Optional<String> findNameByCode(String code);
}