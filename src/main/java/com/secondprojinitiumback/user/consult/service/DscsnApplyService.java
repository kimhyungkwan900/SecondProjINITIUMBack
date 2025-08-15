package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnApplyRequestDto;
import com.secondprojinitiumback.user.consult.repository.DscsnApplyRepoistory;
import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import com.secondprojinitiumback.user.consult.repository.DscsnKindRepository;
import com.secondprojinitiumback.user.consult.repository.DscsnScheduleRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnApplyService {
    private final DscsnApplyRepoistory dscsnApplyRepoistory;
    private final DscsnInfoRepository dscsnInfoRepository;
    private final DscsnScheduleRepository dscsnScheduleRepository;
    private final StudentRepository studentRepository;
    private final DscsnKindRepository dscsnKindRepository;

    private final DscsnInfoService dscsnInfoService;

    //--- 학생 상담신청
    public void applyConsultation(DscsnApplyRequestDto dscsnApplyRequestDto) {

        //선택한 날짜 정보 가져오기
        DscsnSchedule applyDate = dscsnScheduleRepository.findById(dscsnApplyRequestDto.getDscsnDtId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_SCHEDULE_NOT_FOUND));

        //선택한 날짜의 예약여부 변경
        applyDate.updateDscsnYn();

        //학생정보 가져오기
        Student student = studentRepository.findById(dscsnApplyRequestDto.getStudentNo())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

        //상담항목 가져오기
        DscsnKind dscsnKind = dscsnKindRepository.findById(dscsnApplyRequestDto.getDscsnKindId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_KIND_NOT_FOUND));

        //상담신청 ID 생성
        //지도교수 상담은 A, 진로취업 상담은 C, 심리상담은 P, 학습상담은 L
        String keyword = dscsnApplyRequestDto.getDscsnKindId().substring(0,1);

        //1. 시퀀스 번호 생성
        String seqNum = getNextApplySequence(keyword);

        //2. ID 생성
        String dscsnApplyId = keyword + seqNum;

        //엔티티 생성
        DscsnApply dscsnApply = DscsnApply.builder()
                .dscsnApplyId(dscsnApplyId)
                .studentTelno(dscsnApplyRequestDto.getStudentTelno())
                .dscsnApplyCn(dscsnApplyRequestDto.getDscsnApplyCn())
                .student(student)
                .dscsnDt(applyDate)
                .dscsnKind(dscsnKind)
                .build();

        //상담신청 정보 저장
        dscsnApplyRepoistory.save(dscsnApply);

        //상담 정보 생성
        dscsnInfoService.createDscsnInfo(dscsnApply);
    }

    //--- 학생 상담신청 취소
    public void cancelConsultation(String dscsnInfoId) {

        //상담정보 조회
        DscsnInfo dscsnInfo = dscsnInfoRepository.findById(dscsnInfoId).orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_INFO_NOT_FOUND));

        //상담상태가 이미 완료인 경우 예외 처리
        if(dscsnInfo.getDscsnStatus().equals("상담완료")) {
            throw new CustomException(ErrorCode.CONSULTATION_ALREADY_COMPLETED);
        }//상담상태가 이미 취소된 경우 예외 처리
        else if (dscsnInfo.getDscsnStatus().equals("상담취소")) {
            throw new CustomException(ErrorCode.CONSULTATION_ALREADY_CANCELLED);
        }

        //상담정보 ID로 상담상태 상담취소로 변경
        dscsnInfoService.updateDscsnStatus(dscsnInfoId, "상담취소");

        //상담일정 예약여부 변경
        DscsnSchedule dscsnSchedule = dscsnInfo.getDscsnApply().getDscsnDt();
        dscsnSchedule.updateDscsnYn();
    }

    //시퀀스 번호 생성 메소드
    public String getNextApplySequence(String prefix) {
        DscsnApply lastDscsnApply = dscsnApplyRepoistory.findTopByDscsnApplyIdStartingWithOrderByDscsnApplyIdDesc(prefix);

        if(lastDscsnApply == null) {
            return String.format("%04d", 1);
        }
        else{
            String lastId = lastDscsnApply.getDscsnApplyId();
            String seqPart = lastId.substring(prefix.length()); // 접두사 이후 부분 추출
            int seqNum = Integer.parseInt(seqPart); // 문자열을 정수로 변환
            seqNum++; // 시퀀스 번호 증가
            return String.format("%04d", seqNum); // 4자리 문자열로 포맷팅
        }
    }
}

