package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.dto.DscsnInfoDto;
import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import com.secondprojinitiumback.user.consult.repository.SequenceGenerator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnInfoService {
    private final DscsnInfoRepository dscsnInfoRepository;
    private final SequenceGenerator sequenceGenerator;

    //--- 상담정보 생성
    public void createDscsnInfo(DscsnApply dscsnApply) {

        //상담정보 ID 생성
        //1. 시퀀스 번호 생성
        long seqNum = sequenceGenerator.getNextInfoSequence();

        //2. 상담종류 키워드 가져오기
        String keyword = dscsnApply.getDscsnApplyId().substring(0,1);

        //3. ID 생성
        String dscsnInfoId = keyword + "I" + String.format("%04d", seqNum); // I는 상담정보를 나타내는 접두사

        //상담정보 엔티티 생성
        DscsnInfo dscsnInfo = DscsnInfo.builder()
                .dscsnInfoId(dscsnInfoId)
                .dscsnStatus("예약대기") // 초기 상태 설정
                .dscsnResultCn(null) // 상담 결과 내용 초기값
                .dscsnReleaseYn("N") // 상담 결과 공개 여부 초기값
                .dscsnApply(dscsnApply) // 상담 신청 ID
                .build();

        //상담정보 저장
        dscsnInfoRepository.save(dscsnInfo);
    }

    //--- 학생 상담내역 조회
    @Transactional(readOnly = true)
    public Page<DscsnInfoDto> getDscsnInfoByStudentId(Long studentId) {








        // 학생 ID로 상담내역 조회 로직 구현
        // 예: return dscsnInfoRepository.findByStudentId(studentId).getDscsnInfo();
        return null;
    }

    //--- 상담상태 변경
    public void updateDscsnStatus(String dscsnInfoId, String status) {

        // 상담정보 가져오기
        DscsnInfo dscsnInfo = dscsnInfoRepository.findById(dscsnInfoId)
                .orElseThrow(EntityExistsException::new);

        // 상담 상태 업데이트
        dscsnInfo.updateDscsnStatus(status);
    }

    //--- 상담결과 등록
    public void registerDscsnResult(String dscsnInfoId, String releaseYn, String result) {

        // 상담정보 가져오기
        DscsnInfo dscsnInfo = dscsnInfoRepository.findById(dscsnInfoId)
                .orElseThrow(EntityNotFoundException::new);

        // 상담 결과 공개여부 등록
        dscsnInfo.updateDscsnReleaseYn(releaseYn);

        // 상담 결과 등록
        dscsnInfo.updateDscsnResultCn(result);
    }
}
