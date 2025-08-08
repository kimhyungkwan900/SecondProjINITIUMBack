package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DiagnosticAnswerRepository extends JpaRepository<DiagnosticAnswer, Long> {

    /**
     * 특정 질문(questionId)에 속한 모든 보기(Answer) 조회
     * - N:1 관계에서 FK(questionId)로 필터링
     */
    List<DiagnosticAnswer> findByQuestionId(Long questionId);

    /**
     * 특정 검사(testId)에 속한 모든 보기 삭제
     * - 질문(question) → 검사(test)로 접근하여 testId 조건으로 삭제
     * - @Modifying: SELECT가 아닌 DML(DELETE) 쿼리임을 명시
     * - clearAutomatically / flushAutomatically: 영속성 컨텍스트와 DB 동기화
     * - @Transactional: 삭제 작업 트랜잭션 보장
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM DiagnosticAnswer a WHERE a.question.test.id = :testId")
    void deleteAnswersByTestId(@Param("testId") Long testId);
}
