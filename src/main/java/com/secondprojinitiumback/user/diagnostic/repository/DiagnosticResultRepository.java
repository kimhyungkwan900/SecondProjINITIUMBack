package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, Long> {
    List<DiagnosticResult> findByUserId(Long userId); // 사용자의 전체 검사 결과 조회

    List<DiagnosticResult> findByTestIdAndUserId(Long testId, Long userId); // 특정 검사에 대한 결과
}
