package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.*;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreCompetencyResultService;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/core-competency/result")
@RequiredArgsConstructor
public class AdminResultStatisticsController {

    private final AdminCoreCompetencyResultService resultService;
    private final StudentRepository studentRepository;

    /**
     * 특정 평가에 참여한 학생들의 기본 정보 '목록'을 조회
     */
    @GetMapping("/assessments/{assessmentNo}/response/students")
    public List<StudentInfoDto> getStudentListForAssessment(@PathVariable String assessmentNo) {
        // Service 호출 시 두 파라미터를 모두 전달
        return resultService.getStudentListForAssessment(assessmentNo);
    }

    /**
     * 특정 학생의 평가 응답(문항 번호 → 선택지 라벨) 맵을 조회
     */
    @GetMapping("/assessments/{assessmentNo}/response/students/{studentNo}")
    public List<StudentResponseDetailDto> getStudentResponseDetails(@PathVariable String assessmentNo, @PathVariable String studentNo) {
        return resultService.getStudentResponseDetails(assessmentNo, studentNo);
    }

    /**
     * 하위역량별 평균점수(모든 문항 필수 응답 가정) 조회
     */

    @GetMapping("/assessments/{assessmentNo}/students/{studentNo}/sub-competency/avg")
    public List<SubCompetencyAverageDto> getSubAvg(
            @PathVariable String assessmentNo, @PathVariable String studentNo) {
        return resultService.getSubCompetencyAverages(assessmentNo, studentNo);
    }




}
