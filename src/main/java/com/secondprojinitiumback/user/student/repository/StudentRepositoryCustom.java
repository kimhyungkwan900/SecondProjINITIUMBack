package com.secondprojinitiumback.user.student.repository;

import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.dto.StudentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> search(StudentSearchDto studentSearchDto);
    Page<Student> searchPage(StudentSearchDto studentSearchDto, Pageable pageable);
}