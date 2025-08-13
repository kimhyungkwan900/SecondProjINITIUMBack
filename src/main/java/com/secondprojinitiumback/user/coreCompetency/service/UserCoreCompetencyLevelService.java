//package com.secondprojinitiumback.user.coreCompetency.service;
//
//import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResponse;
//import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
//import com.secondprojinitiumback.user.coreCompetency.dto.SubCompetencyLevelRowDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class UserCoreCompetencyLevelService {
//
//    private final CoreCompetencyResponseRepository responseRepository;
//
//    /**
//     * 하위역량별: 나의 평균 vs 코호트 평균 → 우수/보통/미흡 분류
//     * band: 민감도(예: 0.3)
//     */
//    @Transactional
//    public List<SubCompetencyLevelRowDto> getLevels(String assessmentNo, String studentNo, double band) {
//
//        //1. 해당 학생의 응답(해당 평가) 로드
//        List<CoreCompetencyResponse> responses = responseRepository.findAllByAssessment_AssessmentNoAndStudent_StudentNo(assessmentNo, studentNo);
//
//        //2. 하위역량과 핵심역량 정보
//
//    }
//
//}
