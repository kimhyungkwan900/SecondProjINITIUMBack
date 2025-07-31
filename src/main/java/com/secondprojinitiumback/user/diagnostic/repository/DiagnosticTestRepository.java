package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticTestRepository extends JpaRepository<DiagnosticTest, Long> {
    List<DiagnosticTest> findByUseYn(String useYn); // 활성화된 검사만 조회

    List<DiagnosticTest> findByNameContainingIgnoreCaseAndUseYn(String keyword, String useYn);


    Page<DiagnosticTest> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}