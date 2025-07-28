package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalDiagnosticResultRepository extends JpaRepository<ExternalDiagnosticResult, Long> {
    List<ExternalDiagnosticResult> findByUserId(Long userId);

    Optional<ExternalDiagnosticResult> findByInspectCode(String inspectCode);
}