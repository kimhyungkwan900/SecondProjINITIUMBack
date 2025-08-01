package com.secondprojinitiumback.user.student.service.serviceinterface;

import com.secondprojinitiumback.user.student.constant.StudentStatus;
import com.secondprojinitiumback.user.student.dto.*;

import java.util.List;

public interface StudentService {

    // 학생 입학 (최초 등록)
    void enrollStudent(EnrollStudentDto addStudentDto);

    // 학생 상태 변경 (단일 메서드로 통합)
    void changeStudentStatus(String studentNo, String statusCode);

    // 어드민 학생정보 수정
    void adminUpdateStudentInfo(String studentNo, AdminUpdateStudentDto updateDto);
    void updateMyInfo(String studentNo, UpdateStudentDto dto);


    // 학생 단건/리스트 조회
    StudentDto getStudent(String studentNo);
    List<StudentDto> getStudentList(StudentSearchDto cond);

}