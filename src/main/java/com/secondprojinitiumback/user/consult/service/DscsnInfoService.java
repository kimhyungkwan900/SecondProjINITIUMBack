package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.dto.*;
import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnInfoService {
    private final DscsnInfoRepository dscsnInfoRepository;
//    private final SequenceGenerator sequenceGenerator;

    //--- 상담정보 생성
    public void createDscsnInfo(DscsnApply dscsnApply) {

        //상담정보 ID 생성
        //1. 상담종류 키워드 가져오기
        String prefix = dscsnApply.getDscsnApplyId().substring(0,1) + "I";

        //2. 시퀀스 번호 생성
        String seqNum = getNextInfoSequence(prefix);

        //3. ID 생성
        String dscsnInfoId = prefix + String.format("%04d", seqNum); // I는 상담정보를 나타내는 접두사

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
    public Page<DscsnInfoDto> getDscsnInfo(DscsnInfoSearchDto dscsnInfoSearchDto, Pageable pageable) {

        Page<DscsnInfo> dscsnInfoPage = dscsnInfoRepository.getDscsnInfoPageByCondition(dscsnInfoSearchDto, pageable);

//        return dscsnInfoPage.map(dscsnInfo ->
//                DscsnInfoDto.builder()
//                        .dscsnInfoId(dscsnInfo.getDscsnInfoId())
//                        .dscsnStatus(dscsnInfo.getDscsnStatus())
//                        .dscsnResultCn(dscsnInfo.getDscsnResultCn())
//                        .dscsnReleaseYn(dscsnInfo.getDscsnReleaseYn())
//                        .dscsnApplyDto(dscsnInfo.getDscsnApply() != null ?
//                            DscsnApplyResponseDto.builder()
//                                    .dscsnApplyId(dscsnInfo.getDscsnApply().getDscsnApplyId())
//                                    .studentTelno(dscsnInfo.getDscsnApply().getStudentTelno())
//                                    .dscsnApplyCn(dscsnInfo.getDscsnApply().getDscsnApplyCn())
//                                    .dscsnOnlineYn(dscsnInfo.getDscsnApply().getDscsnOnlineYn())
//                                    .studentDto(dscsnInfo.getDscsnApply().getStudent() != null ?
//                                            StudentDto.builder()
//                                                    .studentNo(dscsnInfo.getDscsnApply().getStudent().getStudentNo())
//                                                    .schoolSubject(dscsnInfo.getDscsnApply().getStudent().getSchoolSubject())
//                                                    .name(dscsnInfo.getDscsnApply().getStudent().getName())
//                                                    .build()
//                                                    :null
//                                    )
//                                    .dscsnScheduleDto(dscsnInfo.getDscsnApply().getDscsnDt() != null ?
//                                            DscsnScheduleResponseDto.builder()
//                                                    .scheduleDate(dscsnInfo.getDscsnApply().getDscsnDt().getPossibleDate())
//                                                    .startTime(dscsnInfo.getDscsnApply().getDscsnDt().getPossibleTime())
//                                                    .empNo(dscsnInfo.getDscsnApply().getDscsnDt().getEmployee().getEmpNo())
//                                                    .empName(dscsnInfo.getDscsnApply().getDscsnDt().getEmployee().getName())
//                                                    .schoolSubject(dscsnInfo.getDscsnApply().getDscsnDt().getEmployee().getSchoolSubject().getSubjectName())
//                                                    .build()
//                                            :null
//                                    )
//                                    .dscsnKindDto(dscsnInfo.getDscsnApply().getDscsnKind() != null ?
//                                            DscsnKindDto.builder()
//                                                    .dscsnKindName(dscsnInfo.getDscsnApply().getDscsnKind().getDscsnKindName())
//                                                    .dscsnTypeName(dscsnInfo.getDscsnApply().getDscsnKind().getDscsnTypeName())
//                                                    .build()
//                                            :null
//                                    )
//                                    .build()
//                                    :null
//                        )
//                        .build()
//        );
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

        // 상담 상태 업데이트
        dscsnInfo.updateDscsnStatus("상담완료");
    }

    //시퀀스 번호 생성 메소드
    public String getNextInfoSequence(String prefix) {
        DscsnInfo lastDscsnInfo = dscsnInfoRepository.findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc(prefix);

        if(lastDscsnInfo == null) {
            return String.format("%04d", 1);
        }
        else{
            String lastId = lastDscsnInfo.getDscsnInfoId();
            String seqPart = lastId.substring(prefix.length()); // 접두사 이후 부분 추출
            int seqNum = Integer.parseInt(seqPart); // 문자열을 정수로 변환
            seqNum++; // 시퀀스 번호 증가
            return String.format("%04d", seqNum); // 4자리 문자열로 포맷팅
        }
    }
}
