package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.*;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnApplyRequestDto;
import com.secondprojinitiumback.user.consult.repository.*;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DscsnApplyServiceTest {

    @Mock
    private DscsnApplyRepoistory applyRepo;

    @Mock
    private DscsnScheduleRepository scheduleRepo;

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private DscsnKindRepository kindRepo;

    @Mock
    private DscsnInfoService infoService;

    @InjectMocks
    private DscsnApplyService service;

    private DscsnSchedule sampleSchedule;
    private Student sampleStudent;
    private DscsnKind sampleKind;

    @BeforeEach
    void setup() {
        // 공통으로 쓸 샘플 엔티티
        sampleSchedule = DscsnSchedule.builder()
                .dscsnDtId("A250804003")
                .dscsnYn("N")
                .build();
        sampleStudent = Student.builder()
                .studentNo("S250804003")
                .name("홍길동")
                .build();

        sampleKind = new DscsnKind("A101", "진로관련 상담", "지도교수 상담");
    }

    //--- applyConsultation 성공 경로
    @Test
    void applyConsultation_success() {
        // given
        DscsnApplyRequestDto applyRequestDto = DscsnApplyRequestDto.builder()
                .dscsnDtId("A250804003")
                .studentNo("S250804003")
                .dscsnKindId("A101")
                .studentTelno("010-1234-5678")
                .dscsnApplyCn("진로에 대해 고민이 있습니다.")
                .build();

        when(scheduleRepo.findById("A250804003"))
                .thenReturn(Optional.of(sampleSchedule));
        when(studentRepo.findById("S250804003"))
                .thenReturn(Optional.of(sampleStudent));
        when(kindRepo.findById("A101"))
                .thenReturn(Optional.of(sampleKind));

        // 마지막 시퀀스가 없으면 첫 호출 → "0001"
        when(applyRepo.findTopByDscsnApplyIdStartingWithOrderByDscsnApplyIdDesc("A"))
                .thenReturn(null);

        // when
        service.applyConsultation(applyRequestDto);

        // then
        // 1) 스케줄 예약 상태 토글 호출 확인
        assertThat(sampleSchedule.getDscsnYn()).isEqualTo("Y");

        // 2) Repository.save 호출, 저장된 객체의 ID 검증
        ArgumentCaptor<DscsnApply> captor = ArgumentCaptor.forClass(DscsnApply.class);
        verify(applyRepo).save(captor.capture());
        DscsnApply saved = captor.getValue();
        assertThat(saved.getDscsnApplyId()).isEqualTo("A0001");
        assertThat(saved.getStudent()).isSameAs(sampleStudent);
        assertThat(saved.getDscsnDt()).isSameAs(sampleSchedule);
        assertThat(saved.getDscsnKind()).isSameAs(sampleKind);

        // 3) DscsnInfoService.createDscsnInfo 호출 확인
        verify(infoService).createDscsnInfo(saved);

        // 4) 다른 예외나 추가 호출 없었는지 검증
        verifyNoMoreInteractions(applyRepo, scheduleRepo, studentRepo, kindRepo, infoService);
    }

    //--- applyConsultation: 스케줄이 없으면 예외
    @Test
    void applyConsultation_missingSchedule_throws() {
        when(scheduleRepo.findById("A250804003")).thenReturn(Optional.empty());
        DscsnApplyRequestDto applyRequestDto = DscsnApplyRequestDto.builder()
                .dscsnDtId("A250804003")
                .build();

        assertThatThrownBy(() -> service.applyConsultation(applyRequestDto))
                .isInstanceOf(EntityExistsException.class);
        verify(scheduleRepo).findById("A250804003");
        verifyNoMoreInteractions(scheduleRepo, studentRepo, kindRepo, applyRepo, infoService);
    }

    //--- cancelConsultation 성공 경로
    @Test
    void cancelConsultation_success() {
        // given
        DscsnApply existing = DscsnApply.builder()
                .dscsnApplyId("A0001")
                .dscsnDt(sampleSchedule)
                .build();
        sampleSchedule.updateDscsnYn();  // 이미 예약된 상태

        when(applyRepo.findById("A0001"))
                .thenReturn(Optional.of(existing));

        // when
        service.cancelConsultation("A0001");

        // then
        // 1) 스케줄 예약 상태 토글 호출 확인
        assertThat(sampleSchedule.getDscsnYn()).isEqualTo("N");

        // 2) deleteById 호출 확인
        verify(applyRepo).deleteById("A0001");
    }

    //--- cancelConsultation: 존재하지 않으면 예외
    @Test
    void cancelConsultation_missingApply_throws() {
        when(applyRepo.findById("A1230")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.cancelConsultation("A1230"))
                .isInstanceOf(EntityExistsException.class);
        verify(applyRepo).findById("A1230");
        verifyNoMoreInteractions(applyRepo);
    }

    //--- getNextApplySequence: 첫 시퀀스
    @Test
    void getNextApplySequence_first() {
        when(applyRepo.findTopByDscsnApplyIdStartingWithOrderByDscsnApplyIdDesc("P"))
                .thenReturn(null);
        String next = service.getNextApplySequence("P");
        assertThat(next).isEqualTo("0001");
    }

    //--- getNextApplySequence: 기존 마지막이 "L0042" → "0043"
    @Test
    void getNextApplySequence_increment() {
        DscsnApply last = DscsnApply.builder()
                .dscsnApplyId("L0042")
                .build();
        when(applyRepo.findTopByDscsnApplyIdStartingWithOrderByDscsnApplyIdDesc("L"))
                .thenReturn(last);

        String next = service.getNextApplySequence("L");
        assertThat(next).isEqualTo("0043");
    }
}
