package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DiagnosticResultDetailRepository extends JpaRepository<DiagnosticResultDetail, Long> {
    /**
     * 특정 결과(resultId)에 대한 모든 상세 응답 조회
     * - 한 번의 검사 결과에 포함된 모든 문항 응답을 가져옴
     */
    List<DiagnosticResultDetail> findByResultId(Long resultId); // 결과에 대한 상세 응답

    /**
     * 특정 검사(testId)에 속한 모든 결과 상세(DiagnosticResultDetail) 삭제
     * - @Modifying: JPQL DELETE 실행
     * - clearAutomatically / flushAutomatically: 영속성 컨텍스트 동기화
     * - @Transactional: 삭제 작업 트랜잭션 보장
     * - 서브쿼리를 사용해 DiagnosticResult → DiagnosticTest 관계를 통해 testId로 필터링
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
      DELETE FROM DiagnosticResultDetail d
      WHERE d.result.id IN (
        SELECT r.id FROM DiagnosticResult r
        WHERE r.test.id = :testId
      )
    """)// 해당 검사(testId)에 속한 모든 결과 상세 삭제
    void deleteDetailsByTestId(@Param("testId") Long testId);
}
