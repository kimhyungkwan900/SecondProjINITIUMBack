package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnInfoService {
    private final DscsnInfoRepository dscsnInfoRepository;

    //--- 상담정보 저장
    public void saveDscsnInfo(String dscsnInfo) {
        // 상담정보 저장 로직 구현
        // 예: dscsnInfoRepository.save(new DscsnInfo(dscsnInfo));
    }

    //--- 학생 상담내역 조회
    public String getDscsnInfoByStudentId(Long studentId) {
        // 학생 ID로 상담내역 조회 로직 구현
        // 예: return dscsnInfoRepository.findByStudentId(studentId).getDscsnInfo();
        return "상담내역"; // 임시 반환값
    }

    //--- 상담상태 변경

    //--- 상담결과 등록
}
