package com.secondprojinitiumback.user.student.repository;

import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // 학생 번호로 학생을 찾는 메소드 시작은 입학년도로 시작하여
    Optional<String> findTopByStudentNoStartingWithAndStudentNoContainingOrderByStudentNoDesc(String admissionYear, String departmentCode);
}
