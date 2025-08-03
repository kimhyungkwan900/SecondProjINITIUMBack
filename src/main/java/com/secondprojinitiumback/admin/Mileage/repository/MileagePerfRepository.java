package com.secondprojinitiumback.admin.Mileage.repository;


import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MileagePerfRepository extends JpaRepository<MileagePerf, Long> {

    @EntityGraph(attributePaths = {
            "student",
            "student.schoolSubject",
            "mileageItem",
            "mileageItem.program",
            "scorePolicy"
    })
    @Query("SELECT m FROM MileagePerf m " +
            "WHERE (:studentNo IS NULL OR m.student.studentNo = :studentNo) " +
            "AND (:studentName IS NULL OR m.student.name LIKE %:studentName%) " +
            "AND (:subjectName IS NULL OR m.student.schoolSubject.subjectName LIKE %:subjectName%)")
    Page<MileagePerf> searchWithPaging(
            @Param("studentNo") String studentNo,
            @Param("studentName") String studentName,
            @Param("subjectName") String subjectName,
            Pageable pageable
    );
}
