package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularApplyUpdateDTO;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 비교과 프로그램 신청 관리 서비스
@Service
@RequiredArgsConstructor
public class ExtracurricularApplyAdminService {

    private final ExtracurricularApplyRepository extracurricularApplyRepository;
    private final ModelMapper modelMapper;

    // 비교과 프로그램 신청 목록 조회
    public List<ExtracurricularApplyUpdateDTO> findExtracurricularApplyList(Long eduMngId,AprySttsNm aprySttsNm) {
        // eduMngId와 aprySttsNm에 해당하는 비교과 프로그램 신청 목록을 조회
        List<ExtracurricularApply> applyList = extracurricularApplyRepository.findByExtracurricularProgram_EduMngIdAndAprySttsNm(eduMngId, aprySttsNm);
        // 신청 목록을 ExtracurricularApplyUpdateDTO로 변환하여 반환
        return applyList.stream()
                .map(apply -> modelMapper.map(apply, ExtracurricularApplyUpdateDTO.class))
                .collect(Collectors.toList());
    }

    public void updateExtracurricularApplyStatusBatch(List<ExtracurricularApplyUpdateDTO> dtos) {
        List<Long> ids = dtos.stream().map(ExtracurricularApplyUpdateDTO::getEduAplyId).collect(Collectors.toList());
        List<ExtracurricularApply> applies = extracurricularApplyRepository.findAllById(ids);

        // DTO Map: id -> DTO
        Map<Long, AprySttsNm> statusMap = dtos.stream()
                .collect(Collectors.toMap(ExtracurricularApplyUpdateDTO::getEduAplyId, ExtracurricularApplyUpdateDTO::getAprySttsNm));

        for (ExtracurricularApply apply : applies) {
            AprySttsNm newStatus = statusMap.get(apply.getEduAplyId());
            if (newStatus != null) {
                apply.setAprySttsNm(newStatus);
            }
        }
        extracurricularApplyRepository.saveAll(applies);
    }

}
