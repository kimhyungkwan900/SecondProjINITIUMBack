package com.secondprojinitiumback.user.diagnostic.repository;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface DiagnosticResultDetailRepository extends JpaRepository<DiagnosticResultDetail, Long> {

    List<DiagnosticResultDetail> findByResult_Id(Long resultId);

    // 🔹 문항 참조 카운트(삭제 안전성 판단용)
    long countByQuestion_Id(Long questionId);

    // (선택) 여러 문항에 대해 한 번에 확인/삭제할 때 사용 가능
    long countByQuestion_IdIn(Collection<Long> questionIds);
}
