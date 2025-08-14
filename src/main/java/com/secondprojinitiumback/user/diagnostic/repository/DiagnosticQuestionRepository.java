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

    @EntityGraph(attributePaths = {"answers"})
    List<DiagnosticQuestion> findByTest_IdOrderByOrderAsc(Long testId);

    // (선택) 흔히 쓰는 존재 확인
    boolean existsById(Long id);
}
