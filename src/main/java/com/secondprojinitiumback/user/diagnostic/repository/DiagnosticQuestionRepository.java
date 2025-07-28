package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticQuestionRepository extends JpaRepository<DiagnosticQuestion, Long> {
    List<DiagnosticQuestion> findByTestIdOrderByOrderAsc(Long testId); // 검사별 문항 목록 조회
}
