package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticQuestion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DiagnosticQuestionRepository extends JpaRepository<DiagnosticQuestion, Long> {


    /**
     * 특정 검사(testId)에 속한 모든 문항 삭제
     * - @Modifying: JPQL DELETE 실행
     * - clearAutomatically / flushAutomatically: 영속성 컨텍스트 동기화 보장
     * - @Transactional: 삭제 작업을 트랜잭션 내에서 실행
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM DiagnosticQuestion q WHERE q.test.id = :testId")
    void deleteQuestionsByTestId(@Param("testId") Long testId);

    @EntityGraph(attributePaths = {"answers"})
    List<DiagnosticQuestion> findByTest_IdOrderByOrderAsc(Long testId);
}
