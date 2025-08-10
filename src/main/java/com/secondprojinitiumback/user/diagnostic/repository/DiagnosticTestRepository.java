package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    // 삭제 안 된 전체
    List<DiagnosticTest> findAllByDelYn(String delYn);

    // 검색 (삭제 안 된 것만)
    List<DiagnosticTest> findByNameContainingIgnoreCaseAndDelYn(String keyword, String delYn);

    // 페이징 검색 (삭제 안 된 것만)
    Page<DiagnosticTest> findByNameContainingIgnoreCaseAndDelYn(String keyword, String delYn, Pageable pageable);

    // 존재 + 삭제 안 됨
    boolean existsByIdAndDelYn(Long id, String delYn);

    @EntityGraph(attributePaths = {
            "questions",
            "questions.answers",
            "scoreLevels"
    })
    Optional<DiagnosticTest> findById(Long id);

    Optional<DiagnosticTest> findWithChildrenById(Long id);

}