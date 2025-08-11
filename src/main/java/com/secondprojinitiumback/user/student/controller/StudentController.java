package com.secondprojinitiumback.user.student.controller;

import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.user.student.dto.AdminUpdateStudentDto;
import com.secondprojinitiumback.user.student.dto.EnrollStudentDto;
import com.secondprojinitiumback.user.student.dto.StudentDto;
import com.secondprojinitiumback.user.student.dto.StudentSearchDto;
import com.secondprojinitiumback.user.student.dto.UpdateStudentDto;
import com.secondprojinitiumback.user.student.service.serviceinterface.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import com.secondprojinitiumback.common.dto.request.StatusChangeRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // 학생 입학 (최초 등록)
    @PostMapping
    public ResponseEntity<StudentDto> enrollStudent(@RequestBody EnrollStudentDto dto) {
        // 학생 등록 처리
        StudentDto createdStudent = studentService.enrollStudent(dto);

        // 생성된 학생 정보의 URI를 생성하여 응답
        URI location = URI.create(String.format("/api/students/%s", createdStudent.getStudentNo()));
        return ResponseEntity.created(location).body(createdStudent);
    }

    // 학생 정보 수정 (개인용)
    @PutMapping("/{studentNo}/my-info")
    public ResponseEntity<StudentDto> updateMyInfo(
            @PathVariable String studentNo,
            @RequestBody UpdateStudentDto dto,
            @AuthenticationPrincipal UserDetails userDetails // ← 추가 권장
    ) {
        // 본인만 수정 가능하도록 보호
        if (userDetails == null || !studentNo.equals(userDetails.getUsername())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        StudentDto updatedStudent = studentService.updateMyInfo(studentNo, dto);
        return ResponseEntity.ok(updatedStudent);
    }

    // 학생 정보 수정 (관리자)
    @PutMapping("/{studentNo}/admin-info")
    public ResponseEntity<StudentDto> adminUpdateStudentInfo(@PathVariable String studentNo, @RequestBody AdminUpdateStudentDto dto) {
        // 관리자용 학생 정보 수정 처리
        StudentDto updatedStudent = studentService.adminUpdateStudentInfo(studentNo, dto);

        // 수정된 학생 정보를 반환
        return ResponseEntity.ok(updatedStudent);
    }

    // 학생 검색 및 페이징 처리
    @GetMapping
    public ResponseEntity<Page<StudentDto>> searchStudents(
            @ModelAttribute StudentSearchDto searchDto,
            Pageable pageable) {
        // 학생 검색 및 페이징 처리
        Page<StudentDto> studentPage = studentService.getStudentPage(searchDto, pageable);

        // 검색 결과를 반환
        return ResponseEntity.ok(studentPage);
    }

    // 학생 단건 조회
    @GetMapping("/{studentNo}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable String studentNo) {
        // 학생 단건 조회
        StudentDto studentDto = studentService.getStudent(studentNo);

        // 조회된 학생 정보를 반환
        return ResponseEntity.ok(studentDto);
    }

    // 학생 상태 변경 (단일 메서드로 통합)
    @PatchMapping("/{studentNo}/status")
    public ResponseEntity<StudentDto> changeStudentStatus(
            @PathVariable String studentNo,
            @Valid @RequestBody StatusChangeRequest request) {
        // 학생 상태 변경 처리
        StudentDto updatedStudent = studentService.changeStudentStatus(studentNo, request.getStatusCode());

        // 상태 변경된 학생 정보를 반환
        return ResponseEntity.ok(updatedStudent);
    }
}