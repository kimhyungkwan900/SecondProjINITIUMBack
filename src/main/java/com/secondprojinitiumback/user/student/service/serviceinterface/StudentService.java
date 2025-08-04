package com.secondprojinitiumback.user.student.service.serviceinterface;

import com.secondprojinitiumback.user.student.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    // 학생 입학 (최초 등록)
    StudentDto enrollStudent(EnrollStudentDto addStudentDto);

    // 학생 상태 변경 (단일 메서드로 통합)
    StudentDto changeStudentStatus(String studentNo, String statusCode);

    // 어드민 학생정보 수정
    StudentDto adminUpdateStudentInfo(String studentNo, AdminUpdateStudentDto updateDto);
    StudentDto updateMyInfo(String studentNo, UpdateStudentDto dto);

    // 학생 단건/리스트/페이지 조회
    StudentDto getStudent(String studentNo);
    Page<StudentDto> getStudentPage(StudentSearchDto cond, Pageable pageable);

}
