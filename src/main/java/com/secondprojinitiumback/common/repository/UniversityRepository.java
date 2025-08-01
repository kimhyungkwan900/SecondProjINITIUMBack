package com.secondprojinitiumback.common.repository;

import com.secondprojinitiumback.common.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, String> {
}
