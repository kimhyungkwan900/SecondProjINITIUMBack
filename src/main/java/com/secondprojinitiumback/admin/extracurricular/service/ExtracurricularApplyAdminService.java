package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularApplyUpdateDTO;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// 비교과 프로그램 신청 관리 서비스
@Service
@RequiredArgsConstructor
public class ExtracurricularApplyAdminService {

    private final ExtracurricularApplyRepository extracurricularApplyRepository;
    private final ModelMapper modelMapper;

    // 비교과 프로그램 신청 목록 조회
    public List<ExtracurricularApplyUpdateDTO> findExtracurricularApplyList(AprySttsNm aprySttsNm) {
        // 신청 상태에 따라 비교과 프로그램 신청 목록을 조회
        List<ExtracurricularApply> applyList = extracurricularApplyRepository.findByAprySttsNm(aprySttsNm);
        // 신청 목록을 ExtracurricularApplyUpdateDTO로 변환하여 반환
        return applyList.stream()
                .map(apply -> modelMapper.map(apply, ExtracurricularApplyUpdateDTO.class))
                .collect(Collectors.toList());
    }

    // 비교과 프로그램 신청 상태 업데이트
    public void updateExtracurricularApplyStatus(Long eduAplyId, AprySttsNm newStatus) {
        // 신청 ID로 비교과 프로그램 신청을 조회
        ExtracurricularApply extracurricularApply = extracurricularApplyRepository.findById(eduAplyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청이 존재하지 않습니다. id=" + eduAplyId));
        // 신청 상태 업데이트
        extracurricularApply.setAprySttsNm(newStatus);
        // 변경된 신청 정보를 저장
        extracurricularApplyRepository.save(extracurricularApply);
    }

}
