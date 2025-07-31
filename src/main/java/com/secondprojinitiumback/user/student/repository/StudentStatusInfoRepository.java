package com.secondprojinitiumback.user.student.repository;

import com.secondprojinitiumback.user.student.domain.StudentStatusInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentStatusInfoRepository extends JpaRepository<StudentStatusInfo, String> {
}
