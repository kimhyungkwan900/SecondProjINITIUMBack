package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalDiagnosticResultRepository extends JpaRepository<ExternalDiagnosticResult, Long> {

    /**
     * 특정 학생(studentNo)의 모든 외부 검사 결과 조회
     * - studentNo: Student 엔티티의 고유 학번/식별자
     * - 외부 진단검사 결과 기록을 학생별로 필터링
     */
    List<ExternalDiagnosticResult> findByStudent_StudentNo(String studentNo);

    /**
     * 외부 진단검사 API에서 발급한 검사 코드(inspectCode)로 결과 조회
     * - 동일 코드로 재조회 시 사용
     * - 반환 타입 Optional: 존재하지 않을 수 있음
     */
    Optional<ExternalDiagnosticResult> findByInspectCode(String inspectCode);
}
