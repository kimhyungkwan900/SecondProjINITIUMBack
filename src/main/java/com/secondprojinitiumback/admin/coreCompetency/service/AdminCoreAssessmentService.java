package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.dto.AssessmentListResponseDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCoreAssessmentService {

    private final CommonCodeRepository commonCodeRepository;
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final StudentRepository studentRepository;
    private final CoreCompetencyResponseRepository responseRepository;

    // 1. 핵심 역량 진단 등록
    public CoreCompetencyAssessment createCoreCompetencyAssessment(CoreCompetencyAssessmentDto assessmentDto) {
        CommonCode semesterCode = commonCodeRepository.findById_CodeAndId_CodeGroup(assessmentDto.getSemesterCode(), "SEMES")
                .orElseThrow(() -> new CustomException(ErrorCode.COMMON_CODE_NOT_FOUND));

        CommonCode onlineExecCode = commonCodeRepository.findById_CodeAndId_CodeGroup(assessmentDto.getOnlineYn(), "ONLYN")
                .orElseThrow(() -> new CustomException(ErrorCode.COMMON_CODE_NOT_FOUND));

        SchoolSubject schoolSubject = schoolSubjectRepository.findBySubjectName(assessmentDto.getDepartmentName())
                .orElseThrow(() -> new CustomException(ErrorCode.SCHOOL_SUBJECT_NOT_FOUND));

        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .assessmentNo(assessmentDto.getAssessmentNo())
                .assessmentName(assessmentDto.getAssessmentName())
                .startDate(assessmentDto.getStartDate())
                .endDate(assessmentDto.getEndDate())
                .registerDate(assessmentDto.getRegisterDate())
                .academicYear(assessmentDto.getAcademicYear())
                .semesterCode(semesterCode)
                .onlineExecCode(onlineExecCode)
                .guideContent(assessmentDto.getGuideContent())
                .schoolSubject(schoolSubject)
                .build();

        return coreCompetencyAssessmentRepository.save(assessment);
    }


    // 2. 핵심 역량 진단 수정
    public CoreCompetencyAssessment updateCoreCompetencyAssessment(Long assessmentId, CoreCompetencyAssessmentDto assessmentDto) {

        CommonCode semesterCode = commonCodeRepository.findById_CodeAndId_CodeGroup(assessmentDto.getSemesterCode(), "SEMES")
                .orElseThrow(() -> new CustomException(ErrorCode.COMMON_CODE_NOT_FOUND));

        CommonCode onlineExecCode = commonCodeRepository.findById_CodeAndId_CodeGroup(assessmentDto.getOnlineYn(), "ONLYN")
                .orElseThrow(() -> new CustomException(ErrorCode.COMMON_CODE_NOT_FOUND));

        SchoolSubject schoolSubject = schoolSubjectRepository.findBySubjectName(assessmentDto.getDepartmentName())
                .orElseThrow(() -> new CustomException(ErrorCode.SCHOOL_SUBJECT_NOT_FOUND));

        CoreCompetencyAssessment existingAssessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ASSESSMENT_NOT_FOUND));

        existingAssessment.setAssessmentNo(assessmentDto.getAssessmentNo());
        existingAssessment.setAssessmentName(assessmentDto.getAssessmentName());
        existingAssessment.setStartDate(assessmentDto.getStartDate());
        existingAssessment.setEndDate(assessmentDto.getEndDate());
        existingAssessment.setRegisterDate(assessmentDto.getRegisterDate());
        existingAssessment.setAcademicYear(assessmentDto.getAcademicYear());
        existingAssessment.setSemesterCode(semesterCode);
        existingAssessment.setOnlineExecCode(onlineExecCode);
        existingAssessment.setGuideContent(assessmentDto.getGuideContent());
        existingAssessment.setSchoolSubject(schoolSubject);

        return coreCompetencyAssessmentRepository.save(existingAssessment);
    }


    // 3. 핵심 역량 진단 삭제
    public void deleteCoreCompetencyAssessment(Long assessmentId) {
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ASSESSMENT_NOT_FOUND));
        coreCompetencyAssessmentRepository.delete(assessment);
    }

    // 4. 핵심 역량 진단 조회
    public CoreCompetencyAssessment getCoreCompetencyAssessment(Long assessmentId) {
        return coreCompetencyAssessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ASSESSMENT_NOT_FOUND));
    }

    // 5. 핵심 역량 진단 목록 전체 조회
    public List<CoreCompetencyAssessment> getAllCoreCompetencyAssessments() {
        return coreCompetencyAssessmentRepository.findAll();
    }

    //6. 학생 응시 여부를 포함한 평가 목록 조회
    @Transactional
    public List<AssessmentListResponseDto> findAssessmentsForStudent(String studentNo) {
        // 1. 현재 학생 엔티티를 조회합니다.
        Student student = studentRepository.findByStudentNo(studentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

        // 2. 모든 활성화된 평가 목록을 가져옵니다.
        List<CoreCompetencyAssessment> assessments = coreCompetencyAssessmentRepository.findAll(); // findAllByIsActive(true) 등

        // 3. 각 평가에 대해 학생의 응시 여부를 확인하고 DTO로 변환합니다.
        return assessments.stream().map(assessment -> {
            // ✨ 핵심 로직: 이 학생이 이 평가에 대한 응답 기록이 있는지 확인합니다.
            boolean isCompleted = responseRepository.existsByStudentAndAssessment(student, assessment);

            // DTO 생성자에 응시 여부를 함께 전달합니다.
            return new AssessmentListResponseDto(assessment, isCompleted);
        }).collect(Collectors.toList());
    }
}

