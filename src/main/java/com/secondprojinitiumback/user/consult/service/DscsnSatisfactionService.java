package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.domain.DscsnSatisfaction;
import com.secondprojinitiumback.user.consult.dto.common.DscsnSatisfactionDto;
import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import com.secondprojinitiumback.user.consult.repository.DscsnSatisfactionRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnSatisfactionService {
    private final DscsnInfoRepository dscsnInfoRepoistory;
    private final DscsnSatisfactionRepository dscsnSatisfactionRepository;

    //--- 상담 만족도 설문 등록
    public void saveDscsnSatisfaction(DscsnSatisfactionDto dscsnSatisfactionDto) {

        //상담만족도 ID 생성
        //1. 상담정보 ID로 상담정보 가져오기
        DscsnInfo dscsnInfo = dscsnInfoRepoistory.findById(dscsnSatisfactionDto.getDscsnInfoId())
                .orElseThrow(EntityExistsException::new);

        //2. 상담종류 키워드 가져오기
        //지도교수 상담은 A, 진로취업 상담은 C, 심리상담은 P, 학습상담은 L
        String prefix = dscsnInfo.getDscsnInfoId().substring(0,1) + "S";

        //3. 시퀀스 번호 생성
        String seqNum = getNextSatisfactionSequence(prefix);

        //4. ID 생성
        String dscsnSatisfyId = prefix + seqNum;

        //엔티티 생성
        DscsnSatisfaction dscsnSatisfaction = DscsnSatisfaction.builder()
                .dscsnSatisfyId(dscsnSatisfyId)
                .dscsnSatisfyScore(dscsnSatisfactionDto.getDscsnSatisfyScore())
                .dscsnImp(dscsnSatisfactionDto.getDscsnImp())
                .dscsnInfo(dscsnInfo) // 상담 정보 엔티티 설정
                .build();

        //상담 만족도 설문 저장
        dscsnSatisfactionRepository.save(dscsnSatisfaction);
    }

    //시퀀스 번호 생성 메소드
    public String getNextSatisfactionSequence(String prefix) {
        DscsnSatisfaction lastDscsnSatisfaction = dscsnSatisfactionRepository.findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc(prefix);

        if(lastDscsnSatisfaction == null) {
            return String.format("%04d", 1);
        }
        else{
            String lastId = lastDscsnSatisfaction.getDscsnSatisfyId();
            String seqPart = lastId.substring(prefix.length()); // 접두사 이후 부분 추출
            int seqNum = Integer.parseInt(seqPart); // 문자열을 정수로 변환
            seqNum++; // 시퀀스 번호 증가
            return String.format("%04d", seqNum); // 4자리 문자열로 포맷팅
        }
    }
}
