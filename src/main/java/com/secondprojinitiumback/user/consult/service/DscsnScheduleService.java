package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import com.secondprojinitiumback.user.consult.dto.DscsnScheduleDto;
import com.secondprojinitiumback.user.consult.repository.DscsnScheduleRepository;
import com.secondprojinitiumback.user.consult.repository.SequenceGenerator;
import com.secondprojinitiumback.user.consult.repository.TempEmployeeRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnScheduleService {
    private final DscsnScheduleRepository dscsnScheduleRepository;
    private final SequenceGenerator sequenceGenerator;

    //<<<교원 정보 가져오는 임시 리포지토리>>>
    private final TempEmployeeRepository employeeRepository;

    //상담사, 교수 일정 등록
    public void saveDscsnSchedule(DscsnScheduleDto dscsnScheduleDto, String dscsnType) {
        //상담일정 ID 생성
        //1. 시퀀스 번호 생성
        long seqNum = sequenceGenerator.getNextScheduleSequence();

        //2. 날짜정보 가져오기
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

        //3. ID 생성
        //dscsnType: 지도교수 상담은 A, 진로취업 상담은 C, 심리상담은 P, 학습상담은 L
        String dscsnDtId = dscsnType + today + String.format("%03d", seqNum);

        //dscsnScheduleDto에서 empNo로 Employee 엔티티 조회(임시)
        Employee employeeInfo = employeeRepository.findById(dscsnScheduleDto.getEmpNo())
                .orElseThrow(EntityExistsException::new);

        // DscsnScheduleDto를 DscsnDate 엔티티로 변환
        DscsnSchedule dscsnSchedule = DscsnSchedule.builder()
                .dscsnDtId(dscsnDtId)
                .possibleDate(dscsnScheduleDto.getScheduleDate())
                .possibleTime(dscsnScheduleDto.getStartTime())
                .dscsnYn("N")   // 예약 여부 초기값 N
                .employee(employeeInfo)
                .build();

        //등록한 상담일정 저장
        dscsnScheduleRepository.save(dscsnSchedule);
    }

    //상담사, 교수 일정 조회


    //상담사, 교수 일정 삭제
    public void deleteDscsnSchedule(String dscsnDtId) {
        //상담일정 ID로 해당 상담일정 조회
        DscsnSchedule dscsnSchedule = dscsnScheduleRepository.findById(dscsnDtId)
                .orElseThrow(() -> new IllegalArgumentException("상담일정이 존재하지 않습니다. ID: " + dscsnDtId));

        //조회한 일정 삭제
        dscsnScheduleRepository.delete(dscsnSchedule);
    }
}
