package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticResultDetailRepository extends JpaRepository<DiagnosticResultDetail, Long> {
    List<DiagnosticResultDetail> findByResultId(Long resultId); // 결과에 대한 상세 응답
}
