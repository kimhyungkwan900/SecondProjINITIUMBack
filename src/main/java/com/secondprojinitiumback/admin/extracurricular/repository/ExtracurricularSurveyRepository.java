package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtracurricularSurveyRepository extends JpaRepository<ExtracurricularSurvey, Long> {
    // 특정 비교과 프로그램 ID에 해당하는 설문이 존재하는지 여부를 확인 (true/false 반환)
    boolean existsByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    // 특정 비교과 프로그램 ID로 등록된 만족도 설문 조회 (1:1 관계 가정 시 사용)
    ExtracurricularSurvey findByExtracurricularProgram_EduMngId(Long eduMngId);

    void deleteByExtracurricularProgram_EduMngId(Long eduMngId);

}
