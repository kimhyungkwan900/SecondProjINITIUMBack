package com.secondprojinitiumback.common.repository;

import com.secondprojinitiumback.common.domain.SchoolSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolSubjectRepository extends JpaRepository<SchoolSubject, String> {
    Optional<SchoolSubject> findByCode(String code);

    Optional<SchoolSubject> findByDeptDivisionCode(String departmentName);

}