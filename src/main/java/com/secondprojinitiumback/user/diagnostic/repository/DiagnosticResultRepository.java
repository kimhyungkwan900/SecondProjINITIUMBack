package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, Long> {
    /**
     * 특정 학생(studentNo)의 모든 검사 결과 조회
     * - studentNo는 Student 엔티티의 고유 식별 번호
     */
    List<DiagnosticResult> findByStudent_StudentNo(String studentNo);

    /**
     * 특정 검사(testId) + 특정 학생(studentNo)의 결과 조회
     * - 주로 한 학생이 해당 검사를 이미 응시했는지 중복 체크할 때 사용 가능
     */
    List<DiagnosticResult> findByTest_IdAndStudent_StudentNo(Long testId, String studentNo);

    /**
     * 특정 검사(testId)에 속한 모든 검사 결과 삭제
     * - @Modifying: JPQL DELETE 실행
     * - clearAutomatically / flushAutomatically: 영속성 컨텍스트와 DB 동기화
     * - @Transactional: 삭제 작업 트랜잭션 보장
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM DiagnosticResult r WHERE r.test.id = :testId")// 검사 ID로 결과 삭제
    void deleteResultsByTestId(@Param("testId") Long testId);

    Page<DiagnosticResult> findByStudent_StudentNoOrderByCompletionDateDesc(
            String studentNo, Pageable pageable
    );
}
