package com.secondprojinitiumback.user.student.repository;

import com.secondprojinitiumback.user.student.domain.StudentStatusId;
import com.secondprojinitiumback.user.student.domain.StudentStatusInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentStatusInfoRepository extends JpaRepository<StudentStatusInfo, StudentStatusId> {
    Optional<StudentStatusInfo> findByIdStudentStatusCodeAndIdStudentStatusCodeSe(
            String employeeStatusCode,
            String employeeStatusCodeSe
    );
}
