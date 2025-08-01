package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnApplyRequestDto;
import com.secondprojinitiumback.user.consult.repository.DscsnApplyRepoistory;
import com.secondprojinitiumback.user.consult.repository.DscsnKindRepository;
import com.secondprojinitiumback.user.consult.repository.DscsnScheduleRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnApplyService {
    private final DscsnApplyRepoistory dscsnApplyRepoistory;
    private final DscsnScheduleRepository dscsnScheduleRepository;
    private final StudentRepository studentRepository;
    private final DscsnKindRepository dscsnKindRepository;

    private final DscsnInfoService dscsnInfoService;

    //--- 학생 상담신청
    public void applyConsultation(DscsnApplyRequestDto dscsnApplyRequestDto) {

        //선택한 날짜 정보 가져오기
        DscsnSchedule applyDate = dscsnScheduleRepository.findById(dscsnApplyRequestDto.getDscsnDtId())
                .orElseThrow(EntityExistsException::new);

        //선택한 날짜의 예약여부 변경
        applyDate.updateDscsnYn();

        //학생정보 가져오기
        Student student = studentRepository.findById(dscsnApplyRequestDto.getStudentNo())
                .orElseThrow(EntityExistsException::new);

        //상담항목 가져오기
        DscsnKind dscsnKind = dscsnKindRepository.findById(dscsnApplyRequestDto.getDscsnKindId())
                .orElseThrow(EntityExistsException::new);

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
    public void cancelConsultation(String dscsnApplyId) {
        //상담신청 ID로 상담신청 정보 조회
        DscsnApply dscsnApply = dscsnApplyRepoistory.findById(dscsnApplyId)
                .orElseThrow(EntityExistsException::new);

        //상담신청 정보 삭제
        dscsnApplyRepoistory.delete(dscsnApply);

        //상담일정 예약여부 변경
        DscsnSchedule dscsnSchedule = dscsnApply.getDscsnDt();
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
