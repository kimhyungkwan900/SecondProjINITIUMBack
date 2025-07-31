package com.secondprojinitiumback.user.student.repository;

import com.secondprojinitiumback.user.student.domain.StudentStatusInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentStatusInfoRepository extends JpaRepository<StudentStatusInfo, String> {
    Optional<StudentStatusInfo> findByStudentStatusCodeAndStudentStatusCodeSe(String code, String groupCode);
}
