package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtracurricularApplyRepository extends JpaRepository<ExtracurricularApply,Long> {

    // 이미 신청한 프로그램인지 확인
    boolean existsByStdntInfoAndExtracurricularProgram(StdntInfo stdntInfo, ExtracurricularProgram extracurricularProgram);
    // 신청한 프로그램의 승인 인원수가 초과했는지 확인
    long countByExtracurricularProgramAndAprySttsNm(ExtracurricularProgram extracurricularProgram, AprySttsNm aprySttsNm);
    // 학생의 신청 내역 조회
    List<ExtracurricularApply> findByStdntInfo_StdntNo(String stdntInfoStdntNo);
    // 신청 내역의 상태별 조회
    List<ExtracurricularApply> findByAprySttsNm(AprySttsNm aprySttsNm);
}
