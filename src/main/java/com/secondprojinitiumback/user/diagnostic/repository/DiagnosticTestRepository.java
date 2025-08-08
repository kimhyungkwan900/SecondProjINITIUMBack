package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticTestRepository extends JpaRepository<DiagnosticTest, Long> {
    /**
     * 검사명에 특정 키워드가 포함된 검사 목록 조회 (대소문자 무시)
     * - 부분 일치 검색
     * - 반환 타입: List (페이징 없이 전체 조회)
     */
    List<DiagnosticTest> findByNameContainingIgnoreCase(String keyword);

    /**
     * 검사명에 특정 키워드가 포함된 검사 목록을 페이징 처리하여 조회
     * - Pageable 파라미터를 통해 페이지 번호, 크기, 정렬 지정 가능
     * - 반환 타입: Page (total count 포함)
     */
    Page<DiagnosticTest> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}