package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalDiagnosticTestRepository extends JpaRepository<ExternalDiagnosticTest, Long> {
    /**
     * CareerNet 등 외부 API에서 제공하는 문항 조회 코드(questionApiCode)로 검사 조회
     * - 외부 진단검사 고유 식별자와 매핑
     * - 반환 타입 Optional: 해당 코드의 검사가 없을 수 있음
     */
    Optional<ExternalDiagnosticTest> findByQuestionApiCode(String code);

    /**
     * 검사명에 특정 키워드가 포함된 외부 검사 목록 조회 (대소문자 무시)
     * - 부분 일치 검색
     * - 전체 결과 반환 (페이징 없음)
     */
    List<ExternalDiagnosticTest> findByNameContainingIgnoreCase(String keyword);

    /**
     * 검사명에 특정 키워드가 포함된 외부 검사 목록을 페이징 처리하여 조회
     * - Pageable로 페이지 번호, 크기, 정렬 조건 지정 가능
     * - Page 객체 반환 (total count 포함)
     */
    Page<ExternalDiagnosticTest> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}