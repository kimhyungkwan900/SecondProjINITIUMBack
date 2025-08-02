package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularScheduleDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtracurricularScheduleService {

    private final ExtracurricularScheduleRepository extracurricularScheduleRepository;
    private final ModelMapper modelMapper;

    public void registerSchedulesAutomatically(
            ExtracurricularProgram program,
            LocalDate startDate,
            LocalDate endDate,
            List<DayOfWeek> repeatDays,
            LocalTime startTime,
            LocalTime endTime
    ) {
        // 생성될 일정들을 담을 리스트
        List<ExtracurricularSchedule> schedules = new ArrayList<>();
        // 시작 날짜부터 종료 날짜까지 하루씩 반복
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // 지정된 요일에 해당하는 날짜인지 확인
            if (repeatDays.contains(date.getDayOfWeek())) {
                ExtracurricularSchedule schedule = new ExtracurricularSchedule();

                // 해당 프로그램과 연관 설정
                schedule.setExtracurricularProgram(program);

                // 교육 시작 일시: 날짜 + 시작 시간
                schedule.setEduDt(LocalDateTime.of(date, startTime));

                // 교육 종료 시간만 저장 (날짜는 eduDt 기준)
                schedule.setEduEdnTm(endTime);

                // 리스트에 추가
                schedules.add(schedule);
            }
        }
        // 생성된 일정 전체를 DB에 저장
        extracurricularScheduleRepository.saveAll(schedules);
    }

    // 특정 프로그램 ID에 해당하는 ExtracurricularSchedule(프로그램 일정) 목록 조회
    public List<ExtracurricularScheduleDTO> getSchedulesByProgramId(Long eduMngId) {
        // 프로그램 ID로 ExtracurricularSchedule 조회
        List<ExtracurricularSchedule> schedules =
                extracurricularScheduleRepository.findExtracurricularSchedulesByExtracurricularProgram_EduMngId(eduMngId);
        // DTO로 변환하여 반환
        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ExtracurricularScheduleDTO.class))
                .toList(); // Java 16 이상일 경우 toList(), 8~11은 collect(Collectors.toList())
    }
}
