package com.secondprojinitiumback.admin.Mileage.repository;


import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    //사용자 마일리지 내역 조회용
    @EntityGraph(attributePaths = {
            "student",
            "student.schoolSubject",
            "mileageItem",
            "mileageItem.program",
            "scorePolicy"
    })

    // 1) 페이지네이션 버전
    Page<MileagePerf> findAllByStudent_StudentNo(String studentNo, Pageable pageable);

    // 2) 전체 리스트 버전
    List<MileagePerf> findAllByStudent_StudentNo(String studentNo);
}
