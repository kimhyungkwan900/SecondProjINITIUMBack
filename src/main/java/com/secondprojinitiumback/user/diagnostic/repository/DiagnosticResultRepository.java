package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, Long> {

    // 학생 번호로 모든 검사 결과 조회
    List<DiagnosticResult> findByStudent_StudentNo(String studentNo);

    // 특정 검사 + 특정 학생 번호로 결과 조회
    List<DiagnosticResult> findByTest_IdAndStudent_StudentNo(Long testId, String studentNo);
}
