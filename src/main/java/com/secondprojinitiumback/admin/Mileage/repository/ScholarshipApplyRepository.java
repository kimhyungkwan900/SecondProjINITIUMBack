package com.secondprojinitiumback.admin.Mileage.repository;

import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScholarshipApplyRepository extends JpaRepository<ScholarshipApply, Long> {

    @EntityGraph(attributePaths = {
            "student",
            "student.schoolSubject",
            "stateCode",
            "bankAccount",
            "bankAccount.bankCode"
    })
    @Query("SELECT s FROM ScholarshipApply s " +
            "WHERE (:studentNo IS NULL OR s.student.studentNo = :studentNo) " +
            "AND (:studentName IS NULL OR s.student.name LIKE %:studentName%) " +
            "AND (:subjectName IS NULL OR s.student.schoolSubject.subjectName LIKE %:subjectName%) " +
            "AND (:stateCode IS NULL OR s.stateCode.id.code = :stateCode)")
    Page<ScholarshipApply> searchWithPaging(
            @Param("studentNo") String studentNo,
            @Param("studentName") String studentName,
            @Param("subjectName") String subjectName,
            @Param("stateCode") String stateCode,
            Pageable pageable
    );

    Page<ScholarshipApply> findAllByStudent_StudentNo(String studentNo, Pageable pageable);
}

