package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularScheduleRepository;
import lombok.RequiredArgsConstructor;
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

    public void registerSchedulesAutomatically(
            ExtracurricularProgram program,
            LocalDate startDate,
            LocalDate endDate,
            List<DayOfWeek> repeatDays,
            LocalTime startTime,
            LocalTime endTime
    ) {
        List<ExtracurricularSchedule> schedules = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (repeatDays.contains(date.getDayOfWeek())) {
                ExtracurricularSchedule schedule = new ExtracurricularSchedule();
                schedule.setExtracurricularProgram(program);

                // eduDt = 시작 날짜 + 시작 시간
                schedule.setEduDt(LocalDateTime.of(date, startTime));

                // eduEdnTm = 종료 시간만 저장
                schedule.setEduEdnTm(endTime);

                schedules.add(schedule);
            }
        }
        // 저장
        extracurricularScheduleRepository.saveAll(schedules);
    }
}
