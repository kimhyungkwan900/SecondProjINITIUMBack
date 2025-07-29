package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnDate;
import com.secondprojinitiumback.user.consult.dto.DscsnScheduleDto;
import com.secondprojinitiumback.user.consult.repository.DscsnScheduleRepository;
import com.secondprojinitiumback.user.consult.repository.SequenceGenerator;
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

    //상담사, 교수 일정 등록
    public void saveDscsnSchedule(DscsnScheduleDto dscsnScheduleDto, String dscsnType) {
        //시퀀스 번호 생성
        long seqNum = sequenceGenerator.getNextScheduleSequence();

        //상담일정 ID 생성을 위한 날짜정보 가져오기
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

        //상담일정 ID 생성
        //dscsnType: 지도교수 상담은 A, 진로취업 상담은 C, 심리상담은 P,  학습상담은 L
        String dscsnDtId = dscsnType + today + String.format("%03d", seqNum);

        // DscsnScheduleDto를 DscsnDate 엔티티로 변환
        DscsnDate dscsnDate = DscsnDate.builder()
                .dscsnDtId(dscsnDtId)
                .possibleDate(dscsnScheduleDto.getScheduleDate())
                .possibleTime(dscsnScheduleDto.getStartTime())
                .employee()
                .build();

        //등록한 상담일정 저장
        dscsnScheduleRepository.save(dscsnDate);
    }
}
