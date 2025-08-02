package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import com.secondprojinitiumback.user.consult.repository.DscsnKindRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnKindService {

    private final DscsnKindRepository dscsnKindRepository;

    //--- 상담 항목 조회
    public void getDscsnKind() {
        // 상담 항목 조회 로직 구현
    }

    //--- 상담 항목 등록
    public void saveDscsnKind(DscsnKindDto dscsnKindDto) {

        // 입력한 상담항목 내용을 DscsnKind 엔티티에 저장
        DscsnKind dscsnKind = DscsnKind.builder()
                .dscsnKindId(dscsnKindDto.getDscsnKindId())
                .dscsnKindName(dscsnKindDto.getDscsnKindName())
                .dscsnTypeName(dscsnKindDto.getDscsnTypeName())
                .build();

        //상담항목 테이블에 저장
        dscsnKindRepository.save(dscsnKind);
    }

    //--- 상담 항목 수정
    public void updateDscsnKind(DscsnKindDto dscsnKindDto) {

        // 입력한 상담항목 ID로 DscsnKind 엔티티 조회
        DscsnKind dscsnKind = dscsnKindRepository.findById(dscsnKindDto.getDscsnKindId())
                .orElseThrow(EntityExistsException::new);

        // DscsnKind 엔티티의 필드 업데이트
        dscsnKind.updateDscsnKindName(dscsnKindDto.getDscsnKindName());
        dscsnKind.updateDscsnTypeName(dscsnKindDto.getDscsnTypeName());
    }

    //--- 상담 항목 삭제
    public void deleteDscsnKind(String dscsnKindId) {

        // 받아온 상담항목 ID로 DscsnKind 엔티티 조회
        DscsnKind dscsnKind = dscsnKindRepository.findById(dscsnKindId)
                .orElseThrow(EntityExistsException::new);

        // DscsnKind 엔티티 삭제
        dscsnKindRepository.delete(dscsnKind);
    }
}
