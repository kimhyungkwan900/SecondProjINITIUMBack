package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.DscsnKindDto;
import com.secondprojinitiumback.user.consult.repository.DscsnKindRepository;
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
        DscsnKind dscsnKind = DscsnKind.builder()
                .dscsnKindId(dscsnKindDto.getDscsnKindId())
                .dscsnKindName(dscsnKindDto.getDscsnKindName())
                .dscsnTypeName(dscsnKindDto.getDscsnTypeName())
                .build();

        dscsnKindRepository.save(dscsnKind);
    }

    //--- 상담 항목 수정
    public void updateDscsnKind(DscsnKindDto dscsnKindDto) {
        DscsnKind dscsnKind = dscsnKindRepository.findById()
        // 상담 항목 수정 로직 구현
    }

    //--- 상담 항목 삭제
    public void deleteDscsnKind() {
        // 상담 항목 삭제 로직 구현
    }
}
