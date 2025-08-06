package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtracurricularApplyRepository extends JpaRepository<ExtracurricularApply,Long> {

    // 특정 학생이 특정 비교과 프로그램에 이미 신청했는지 여부 확인
    boolean existsBystudentAndExtracurricularProgram(Student student, ExtracurricularProgram extracurricularProgram);

    // 특정 비교과 프로그램에 대해 승인된(AprySttsNm 기준) 신청자의 수를 조회
    long countByExtracurricularProgramAndAprySttsNm(ExtracurricularProgram extracurricularProgram, AprySttsNm aprySttsNm);

    // 학생 번호와 삭제 여부를 기준으로 해당 학생의 신청 내역 조회 (삭제된 내역 포함 여부 판단 가능)
    List<ExtracurricularApply> findByStudent_StudentNoAndDelYn(String studentNo, String delYn);

    List<ExtracurricularApply> findByStudent_StudentNo(String studentNo);

    // 신청 상태(AprySttsNm)를 기준으로 신청 내역 조회 (예: 승인됨, 반려됨 등)
    List<ExtracurricularApply> findByAprySttsNm(AprySttsNm aprySttsNm);

    // 비교과 프로그램 ID와 신청 상태를 기준으로 신청 내역 조회 (특정 프로그램의 승인자/대기자 등 필터링 가능)
    List<ExtracurricularApply> findByExtracurricularProgram_EduMngIdAndAprySttsNm(Long eduMngId, AprySttsNm aprySttsNm);

    int countByExtracurricularProgram_EduMngIdAndAprySttsNm(Long extracurricularProgramEduMngId, AprySttsNm aprySttsNm);
    // 위와 동일: 프로그램 ID와 신청 상태를 기준으로 신청 내역 조회 (메서드 명명 방식만 다름)
    List<ExtracurricularApply> findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndAprySttsNm(Long eduMngId, AprySttsNm statusNm);

    // 비교과 프로그램 ID와 학생 번호를 기준으로 특정 학생의 신청 내역을 Optional로 조회 (단일 결과 기대 시 사용)
    Optional<ExtracurricularApply> findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndStudent_studentNo(Long eduMngId, String stdntNo);
}
