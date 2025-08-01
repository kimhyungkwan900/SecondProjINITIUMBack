package com.secondprojinitiumback.user.student.repository;

import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>, StudentRepositoryCustom {

    Optional<String> findTopByStudentNoStartingWithAndStudentNoContainingOrderByStudentNoDesc(String admissionYear, String departmentCode);

    Optional<Student> findByLoginInfoLoginId(String loginId);
}
