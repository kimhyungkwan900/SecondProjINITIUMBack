package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnScheduleRequestDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnScheduleResponseDto;
import com.secondprojinitiumback.user.consult.repository.DscsnScheduleRepository;
import com.secondprojinitiumback.user.consult.repository.TempEmployeeRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnScheduleService {
    private final DscsnScheduleRepository dscsnScheduleRepository;

    //<<<교원 정보 가져오는 임시 리포지토리>>>
    private final TempEmployeeRepository employeeRepository;

    //--- 상담사, 교수 일정 등록
    public void saveDscsnSchedule(List<DscsnScheduleRequestDto> dscsnScheduleRequestDtos) {

        for (DscsnScheduleRequestDto dto : dscsnScheduleRequestDtos) {
            //상담일정 ID 생성

            //1. 날짜정보 가져오기
            String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
            String prefix = dto.getDscsnType() + today; //dscsnType: 지도교수 상담은 A, 진로취업 상담은 C, 심리상담은 P, 학습상담은 L

            //2. 시퀀스 번호 생성
            String seqNum = getNextScheduleSequence(dto.getDscsnType() + today);

            //3. ID 생성
            String dscsnDtId = prefix + seqNum;

            //dscsnScheduleDto에서 empNo로 Employee 엔티티 조회(임시)
            Employee employeeInfo = employeeRepository.findById(dto.getEmpNo())
                    .orElseThrow(EntityExistsException::new);

            // DscsnScheduleDto를 DscsnDate 엔티티로 변환
            DscsnSchedule dscsnSchedule = DscsnSchedule.builder()
                    .dscsnDtId(dscsnDtId)
                    .possibleDate(dto.getScheduleDate())
                    .possibleTime(dto.getStartTime())
                    .dscsnYn("N")   // 예약 여부 초기값 N
                    .employee(employeeInfo)
                    .build();

            //등록한 상담일정 저장
            dscsnScheduleRepository.save(dscsnSchedule);
        }
    }
    //--- 상담사, 교수 일정 단일 조회
    @Transactional(readOnly = true)
    public DscsnScheduleResponseDto getDscsnScheduleById(String dscsnDtId) {
        //상담일정 ID로 해당 상담일정 조회
        DscsnSchedule dscsnSchedule = dscsnScheduleRepository.findById(dscsnDtId)
                .orElseThrow(EntityExistsException::new);

        return DscsnScheduleResponseDto.builder()
                .dscsnDtId(dscsnSchedule.getDscsnDtId())
                .empNo(dscsnSchedule.getEmployee().getEmpNo())
                .empName(dscsnSchedule.getEmployee().getName())
                .schoolSubject(dscsnSchedule.getEmployee().getSchoolSubject().getSubjectName())
                .scheduleDate(dscsnSchedule.getPossibleDate())
                .startTime(dscsnSchedule.getPossibleTime())
                .dscsnYn(dscsnSchedule.getDscsnYn())
                .build();
    }

    //--- 상담사, 교수 상담일정 조회
    @Transactional(readOnly = true)
    public List<DscsnScheduleResponseDto> getDscsnSchedule(String startDay, String endDay, String dscsnType, String empNo) {

        List<DscsnSchedule> dscsnSchedules = dscsnScheduleRepository.findDscsnSchedule(startDay, endDay, dscsnType, empNo);

        return dscsnSchedules.stream()
                .map(dscsnSchedule ->
                DscsnScheduleResponseDto.builder()
                        .dscsnDtId(dscsnSchedule.getDscsnDtId())
                        .empNo(dscsnSchedule.getEmployee().getEmpNo())
                        .empName(dscsnSchedule.getEmployee().getName())
                        .schoolSubject(dscsnSchedule.getEmployee().getSchoolSubject().getSubjectName())
                        .scheduleDate(dscsnSchedule.getPossibleDate())
                        .startTime(dscsnSchedule.getPossibleTime())
                        .dscsnYn(dscsnSchedule.getDscsnYn())
                        .build())
                .toList();
    }

    //--- 상담사, 교수 일정 삭제
    public void deleteDscsnSchedule(List<String> scheduleIds) {
        //상담일정 ID로 해당 상담일정 삭제
        dscsnScheduleRepository.deleteAllById(scheduleIds);
    }

    //시퀀스 번호 생성 메소드
    public String getNextScheduleSequence(String prefix) {
        DscsnSchedule lastDscsnDt = dscsnScheduleRepository.findTopByDscsnDtIdStartingWithOrderByDscsnDtIdDesc(prefix);

        if(lastDscsnDt == null) {
            return String.format("%03d", 1);
        }
        else{
            String lastId = lastDscsnDt.getDscsnDtId();
            String seqPart = lastId.substring(prefix.length()); // 접두사 이후 부분 추출
            int seqNum = Integer.parseInt(seqPart); // 문자열을 정수로 변환
            seqNum++; // 시퀀스 번호 증가
            return String.format("%03d", seqNum); // 3자리 문자열로 포맷팅
        }
    }

}
