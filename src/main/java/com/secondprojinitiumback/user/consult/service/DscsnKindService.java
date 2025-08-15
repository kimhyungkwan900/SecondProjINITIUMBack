package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import com.secondprojinitiumback.user.consult.repository.DscsnKindRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnKindService {

    private final DscsnKindRepository dscsnKindRepository;

    //--- 상담 항목 조회
    public Page<DscsnKindDto> getDscsnKind(DscsnKindDto dscsnKindDto, Pageable pageable) {

        Page<DscsnKind> dscsnKindPage = dscsnKindRepository.getDscsnKindPageByCondition(dscsnKindDto, pageable);

        return dscsnKindPage.map(dscsnKind ->
                DscsnKindDto.builder()
                        .dscsnKindId(dscsnKind.getDscsnKindId())
                        .dscsnKindName(dscsnKind.getDscsnKindName())
                        .dscsnTypeName(dscsnKind.getDscsnTypeName())
                        .build()
        );
    }

    //--- 상담신청 페이지 상담항목 가져오기
    public List<DscsnKindDto> getDscsnKindByPrefix(String prefix) {
        List<DscsnKind> dscsnKinds = dscsnKindRepository.findByDscsnKindIdStartingWith(prefix);

        return dscsnKinds.stream()
                .map(dscsnKind -> DscsnKindDto.builder()
                        .dscsnKindId(dscsnKind.getDscsnKindId())
                        .dscsnKindName(dscsnKind.getDscsnKindName())
                        .dscsnTypeName(dscsnKind.getDscsnTypeName())
                        .build())
                .toList();
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
                .orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_KIND_NOT_FOUND));

        // DscsnKind 엔티티의 필드 업데이트
        dscsnKind.updateDscsnKindName(dscsnKindDto.getDscsnKindName());
        dscsnKind.updateDscsnTypeName(dscsnKindDto.getDscsnTypeName());
    }

    //--- 상담 항목 삭제
    public void deleteDscsnKind(List<String> dscsnKindIds) {

        //받아온 상담항목 코드로 항목 삭제
        dscsnKindRepository.deleteAllById(dscsnKindIds);
    }
}

