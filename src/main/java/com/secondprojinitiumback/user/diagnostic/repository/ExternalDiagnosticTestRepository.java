package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalDiagnosticTestRepository extends JpaRepository<ExternalDiagnosticTest, Long> {
    Optional<ExternalDiagnosticTest> findByQuestionApiCode(String code);

    List<ExternalDiagnosticTest> findByNameContainingIgnoreCase(String keyword);

    Page<ExternalDiagnosticTest> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}