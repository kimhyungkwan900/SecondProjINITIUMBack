package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalDiagnosticResultRepository extends JpaRepository<ExternalDiagnosticResult, Long> {

    /**
     * 특정 학생의 모든 외부 검사 결과 조회
     */
    List<ExternalDiagnosticResult> findByStudent_StudentNo(String studentNo);

    /**
     * 외부 API에서 발급된 검사 코드로 결과 조회
     */
    Optional<ExternalDiagnosticResult> findByInspectCode(String inspectCode);
}
