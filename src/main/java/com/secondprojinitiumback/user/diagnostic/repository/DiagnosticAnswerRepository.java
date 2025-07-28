package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticAnswerRepository extends JpaRepository<DiagnosticAnswer, Long> {
    List<DiagnosticAnswer> findByQuestionId(Long questionId);
}
