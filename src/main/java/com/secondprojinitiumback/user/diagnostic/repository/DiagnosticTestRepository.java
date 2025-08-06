package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticTestRepository extends JpaRepository<DiagnosticTest, Long> {
    List<DiagnosticTest> findByNameContainingIgnoreCase(String keyword);

    // ✅ 전부 제거하고 아래 기본 메서드만 유지
    Page<DiagnosticTest> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}