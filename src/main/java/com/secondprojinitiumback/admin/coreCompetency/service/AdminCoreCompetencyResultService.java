// AdminCoreCompetencyResultService.java
package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResponse;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.StudentInfoDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.StudentResponseDetailDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyAverageDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCoreCompetencyResultService {

    private final CoreCompetencyResponseRepository responseRepository;
    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final SubCompetencyCategoryRepository subCompetencyCategoryRepository;

    /**
     * 특정 평가(assessmentNo)에 참여한 학생 기본정보 목록
     * - 주의: 파라미터에 studentNo를 받지 않는다(전체 학생 목록이 목적이므로)
     */
    public List<StudentInfoDto> getStudentListForAssessment(String assessmentNo) {
        // 평가번호로 모든 응답 조회 → 학생 기준 그룹화
        List<CoreCompetencyResponse> all =
                responseRepository.findAllWithDetailsByAssessment_AssessmentNo(assessmentNo);

        Map<Student, List<CoreCompetencyResponse>> byStudent =
                all.stream().collect(Collectors.groupingBy(CoreCompetencyResponse::getStudent));

        return byStudent.entrySet().stream()
                .map(e -> {
                    Student s = e.getKey();
                    List<CoreCompetencyResponse> rs = e.getValue();
                    // 첫 응답일(문자열 비교 최소값) — 형식이 yyyymmdd 또는 ISO라면 문제 없음
                    String completeDate = rs.stream()
                            .map(CoreCompetencyResponse::getCompleteDate)
                            .filter(Objects::nonNull)
                            .min(String::compareTo)
                            .orElse(null);

                    return StudentInfoDto.builder()
                            .assessmentNo(assessmentNo)
                            .studentNo(s.getStudentNo())
                            .name(s.getName())
                            .gender(s.getGender() != null ? s.getGender().getCodeName() : "")
                            // DTO 필드명이 subjectCode지만 실제로 과명이라면 DTO를 subjectName으로 바꾸는 것을 권장
                            .subjectCode(s.getSchoolSubject() != null ? s.getSchoolSubject().getSubjectName() : "")
                            .schoolYear(String.valueOf(s.getGrade()))
                            .status(s.getStudentStatus() != null ? s.getStudentStatus().getStudentStatusName() : "")
                            .completeDate(completeDate)
                            .build();
                })
                .sorted(Comparator.comparing(StudentInfoDto::getName, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }

    /**
     * 특정 학생의 문항별 선택 라벨 목록
     */
    public List<StudentResponseDetailDto> getStudentResponseDetails(String assessmentNo, String studentNo) {
        List<CoreCompetencyResponse> rs =
                responseRepository.findByStudent_StudentNoAndAssessment_AssessmentNo(studentNo, assessmentNo);

        return rs.stream()
                .map(r -> StudentResponseDetailDto.builder()
                        .assessmentNo(assessmentNo)
                        .studentNo(studentNo)
                        .questionNo(r.getQuestion().getQuestionNo())
                        .label(r.getSelectedOption() != null ? r.getSelectedOption().getLabel() : "선택 없음")
                        .build())
                .sorted(Comparator.comparing(StudentResponseDetailDto::getQuestionNo))
                .collect(Collectors.toList());
    }

    /**
     * 하위역량별 평균점수(모든 문항 필수 응답 가정)
     */
    public List<SubCompetencyAverageDto> getSubCompetencyAverages(String assessmentNo, String studentNo) {
        List<CoreCompetencyResponse> responses =
                responseRepository.findAllByAssessment_AssessmentNoAndStudent_StudentNo(assessmentNo, studentNo);

        // 하위역량별 합계/개수
        Map<Long, DoubleSummaryStatistics> statBySubId = responses.stream()
                .filter(r -> r.getQuestion() != null && r.getQuestion().getSubCompetencyCategory() != null)
                .filter(r -> r.getSelectedOption() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getQuestion().getSubCompetencyCategory().getId(),
                        Collectors.summarizingDouble(r -> Optional.ofNullable(r.getSelectedOption().getScore()).orElse(0))
                ));

        // 응답에서 하위역량 목록(이름순) 추출
        record SC(Long id, String name) {}
        List<SC> subs = responses.stream()
                .map(r -> r.getQuestion().getSubCompetencyCategory())
                .filter(Objects::nonNull)
                .map(sc -> new SC(sc.getId(), sc.getSubCategoryName()))
                .distinct() // equals/hashCode가 id 기반 아니면 아래처럼 id로 중복 제거 권장
                .sorted(Comparator.comparing(SC::name))
                .toList();

        return subs.stream().map(sc -> {
            DoubleSummaryStatistics s = statBySubId.get(sc.id());
            double avg = (s != null && s.getCount() > 0) ? s.getAverage() : 0.0;
            return SubCompetencyAverageDto.builder()
                    .subCategoryId(sc.id())
                    .subCategoryName(sc.name())
                    .questionCount(s != null ? s.getCount() : 0)
                    .totalScore(s != null ? s.getSum()   : 0.0)
                    .average(Math.round(avg * 100.0) / 100.0)
                    .build();
        }).toList();
    }
}
