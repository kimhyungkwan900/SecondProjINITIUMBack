package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduSlctnType;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyDTO;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyFormDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtracurricularApplyService {

    private final ExtracurricularApplyRepository extracurricularApplyRepository;
    private final ExtracurricularProgramRepository extracurricularProgramRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    // 비교과 프로그램 참여 신청
    public void applyExtracurricular(String stdntNo, ExtracurricularApplyFormDTO dto) {
        Student student = studentRepository.findById(stdntNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생 없음: " + stdntNo));

        dto.setEduAplyDt(LocalDateTime.now()); // 신청 일시 설정



        Long eduMngId = dto.getExtracurricularProgram().getEduMngId();
        ExtracurricularProgram program = extracurricularProgramRepository
                .findByEduMngId(eduMngId)
                .orElseThrow(() -> new EntityNotFoundException("프로그램이 존재하지 않습니다."));

        // 중복 신청 방지
        boolean exists = extracurricularApplyRepository.existsBystudentAndExtracurricularProgram(student, program);
        if(exists){
            throw new IllegalArgumentException("이미 신청한 프로그램입니다: " + program.getEduMngId());
        }
        // 신청 기간 확인
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime programStart = program.getEduAplyBgngDt();  // 이 값이 null일 수 있음
        if (now.isBefore(programStart)) {
            throw new RuntimeException("프로그램 신청 기간이 아닙니다.");
        }
        // 선착순 인원 제한
        long acceptCount = extracurricularApplyRepository.countByExtracurricularProgramAndAprySttsNm(program, AprySttsNm.ACCEPT);
        if (acceptCount >= program.getEduPtcpNope()) {
            throw new IllegalStateException("신청 인원이 초과되었습니다.");
        }
        if(program.getEduSlctnType() == EduSlctnType.FIRSTCOME){
            ExtracurricularApply apply = ExtracurricularApply.builder()
                    .student(student)
                    .extracurricularProgram(program)
                    .eduAplyCn(dto.getEduAplyCn())
                    .eduAplyDt(dto.getEduAplyDt())
                    .aprySttsNm(AprySttsNm.ACCEPT) // 선착순 프로그램은 신청 시 바로 승인 처리
                    .delYn("N") // 논리 삭제 여부
                    .build();
            extracurricularApplyRepository.save(apply);
        }else{
            ExtracurricularApply apply = ExtracurricularApply.builder()
                    .student(student)
                    .extracurricularProgram(program)
                    .eduAplyCn(dto.getEduAplyCn())
                    .eduAplyDt(dto.getEduAplyDt())
                    .aprySttsNm(AprySttsNm.APPLY) // 일반 프로그램은 신청 시 승인 대기 상태로 저장
                    .delYn("N") // 논리 삭제 여부
                    .build();
            extracurricularApplyRepository.save(apply);
        }
    }

    // 신청 취소(삭제)
    public void cancelApplyExtracurricular(Long eduAplyId) {
        ExtracurricularApply apply = extracurricularApplyRepository.findById(eduAplyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청이 존재하지 않습니다: " + eduAplyId));
        if (apply.getAprySttsNm() != AprySttsNm.APPLY) {
            throw new IllegalStateException("이미 처리된 신청은 취소할 수 없습니다.");
        }
        extracurricularApplyRepository.delete(apply);
    }

    // 신청 목록 조회
    public Page<ExtracurricularApplyDTO> findExtracurricularApplylist(
            String stdntNo,
            AprySttsNm aprySttsNm,
            String keyword,
            Pageable pageable
    ) {
        studentRepository.findById(stdntNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생 없음: " + stdntNo));

        Page<ExtracurricularApply> applyPage = extracurricularApplyRepository.findApplyWithFilters(
                stdntNo, aprySttsNm, keyword, pageable
        );

        return applyPage.map(apply -> ExtracurricularApplyDTO.builder()
                .eduAplyId(apply.getEduAplyId())
                .eduAplyCn(apply.getEduAplyCn())
                .aprySttsNm(apply.getAprySttsNm())
                .eduAplyDt(apply.getEduAplyDt())
                .programNm(apply.getExtracurricularProgram().getEduNm())
                .categoryNm(apply.getExtracurricularProgram().getExtracurricularCategory().getCtgryNm())
                .build()
        );
    }

    @Transactional
    public void deleteApplyExtracurriculars(List<Long> eduAplyIds) {
        List<ExtracurricularApply> applies = extracurricularApplyRepository.findAllById(eduAplyIds);
        if (applies.size() != eduAplyIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 신청 내역이 포함되어 있습니다.");
        }
        for (ExtracurricularApply apply : applies) {
            apply.setDelYn("Y");
        }
        extracurricularApplyRepository.saveAll(applies);
    }

    // 신청 삭제 처리
    public void deleteApplyExtracurricular(Long eduAplyId) {
        ExtracurricularApply apply = extracurricularApplyRepository.findById(eduAplyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청이 존재하지 않습니다: " + eduAplyId));
        apply.setDelYn("Y");
        extracurricularApplyRepository.save(apply);
    }

}
