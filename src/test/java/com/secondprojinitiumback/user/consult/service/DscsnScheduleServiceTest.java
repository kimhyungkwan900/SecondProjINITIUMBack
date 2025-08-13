package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnScheduleRequestDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnScheduleResponseDto;
import com.secondprojinitiumback.user.consult.repository.DscsnScheduleRepository;
import com.secondprojinitiumback.user.consult.repository.TempEmployeeRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DscsnScheduleServiceTest {

    @Mock
    private DscsnScheduleRepository dscsnScheduleRepository;

    @Mock
    private TempEmployeeRepository employeeRepository;

    @InjectMocks
    private DscsnScheduleService dscsnScheduleService;

    private DscsnScheduleRequestDto dscsnScheduleRequestDto;
    private Employee sampleEmp;
    private SchoolSubject sampleSubject;

    @BeforeEach
    void setUp() {
        dscsnScheduleRequestDto = DscsnScheduleRequestDto.builder()
                .empNo("2021391823") // 임시 사번
                .scheduleDate("250810")
                .startTime("1400")
                .build();

        sampleEmp = mock(Employee.class);
    }

    //--- saveDscsnSchedule: 정상 저장 (첫 시퀀스)
    @Test
    void saveDscsnSchedule_firstSequence() {
        // 오늘 날짜 기반 접두사 계산
        String today = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyMMdd"));
        String prefix = "A" + today;

        // 1) 마지막 스케줄이 없을 때
        when(dscsnScheduleRepository
                .findTopByDscsnDtIdStartingWithOrderByDscsnDtIdDesc(prefix))
                .thenReturn(null);

        // 2) 직원 조회
        when(employeeRepository.findById("2021391823"))
                .thenReturn(Optional.of(sampleEmp));

        // 실행
//        dscsnScheduleService.saveDscsnSchedule(dscsnScheduleRequestDto, "A");

        // 검증: save() 호출
        ArgumentCaptor<DscsnSchedule> captor =
                ArgumentCaptor.forClass(DscsnSchedule.class);
        verify(dscsnScheduleRepository).save(captor.capture());

        DscsnSchedule saved = captor.getValue();
        // ID는 prefix + "001"
        assertThat(saved.getDscsnDtId())
                .isEqualTo(prefix + "001");
        assertThat(saved.getPossibleDate())
                .isEqualTo("250810");
        assertThat(saved.getPossibleTime())
                .isEqualTo("1400");
        assertThat(saved.getDscsnYn())
                .isEqualTo("N");
        assertThat(saved.getEmployee())
                .isSameAs(sampleEmp);
    }

    //--- saveDscsnSchedule: 직원 정보 없으면 예외
    @Test
    void saveDscsnSchedule_missingEmployee_throws() {
        when(employeeRepository.findById("2021391823")).thenReturn(Optional.empty());

//        assertThatThrownBy(() ->
//                dscsnScheduleService.saveDscsnSchedule(dscsnScheduleRequestDto, "A")
//        ).isInstanceOf(EntityExistsException.class);

        verify(employeeRepository).findById("2021391823");
//        verifyNoMoreInteractions(dscsnScheduleRepository);
    }

    //--- getDscsnSchedule: 페이징 + 매핑 검증
//    @Test
//    void getDscsnSchedule_mapsToResponseDto() {
//        // 직원 엔티티와 연관된 Subject 스텁
//        sampleSubject = mock(SchoolSubject.class);
//        when(sampleSubject.getSubjectName()).thenReturn("Engineering");
//
//        when(sampleEmp.getEmpNo()).thenReturn("2021391823");
//        when(sampleEmp.getName()).thenReturn("김교수");
//        when(sampleEmp.getSchoolSubject()).thenReturn(sampleSubject);
//
//        Pageable pageReq = PageRequest.of(0, 1);
//        DscsnSchedule schedule = DscsnSchedule.builder()
//                .dscsnDtId("A" + "dummy")
//                .possibleDate("250810")
//                .possibleTime("1200")
//                .dscsnYn("N")
//                .employee(sampleEmp)
//                .build();
//
//        Page<DscsnSchedule> domainPage =
//                new PageImpl<>(List.of(schedule), pageReq, 1);
//        when(dscsnScheduleRepository.findByEmployee_EmpNo("2021391823", pageReq))
//                .thenReturn(domainPage);
//
//        Page<DscsnScheduleResponseDto> result =
//                dscsnScheduleService.getDscsnSchedule("2021391823", pageReq);
//
//        assertThat(result.getTotalElements()).isEqualTo(1);
//        DscsnScheduleResponseDto r = result.getContent().getFirst();
//        assertThat(r.getEmpNo()).isEqualTo("2021391823");
////        assertThat(r.getEmpName()).isEqualTo("김교수");
////        assertThat(r.getSchoolSubject()).isEqualTo("Engineering");
////        // service 코드에서 scheduleDate를 두 번 호출한 뒤 덮어쓰기하므로,
////        // 최종적으로 possibleTime이 들어감
////        assertThat(r.getScheduleDate()).isEqualTo("1400");
//    }

    //--- deleteDscsnSchedule: 삭제 호출 검증
//    @Test
//    void deleteDscsnSchedule_callsDeleteById() {
//        dscsnScheduleService.deleteDscsnSchedule("A250910002");
//        verify(dscsnScheduleRepository).deleteById("A250910002");
//    }

    //--- getNextScheduleSequence: 첫 시퀀스
    @Test
    void getNextScheduleSequence_first() {
        when(dscsnScheduleRepository
                .findTopByDscsnDtIdStartingWithOrderByDscsnDtIdDesc("A250910"))
                .thenReturn(null);

        String seq = dscsnScheduleService.getNextScheduleSequence("A250910");
        assertThat(seq).isEqualTo("001");
    }

    //--- getNextScheduleSequence: 증가 시퀀스
    @Test
    void getNextScheduleSequence_increment() {
        DscsnSchedule last = DscsnSchedule.builder()
                .dscsnDtId("A250911003")
                .build();

        when(dscsnScheduleRepository
                .findTopByDscsnDtIdStartingWithOrderByDscsnDtIdDesc("A250911"))
                .thenReturn(last);

        String seq = dscsnScheduleService.getNextScheduleSequence("A250911");
        assertThat(seq).isEqualTo("004");
    }
}
