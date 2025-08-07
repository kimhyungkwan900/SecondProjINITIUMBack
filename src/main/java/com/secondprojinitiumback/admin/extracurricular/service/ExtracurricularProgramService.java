package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularImage;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.dto.*;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularCategoryRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularImageRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.specification.ExtracurricularProgramSpecification;
import com.secondprojinitiumback.admin.extracurricular.repository.specification.ProgramFilterRequest;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtracurricularProgramService {

    private final ExtracurricularProgramRepository extracurricularProgramRepository;
    private final ExtracurricularCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final ExtracurricularScheduleService extracurricularScheduleService;

    private final ExtracurricularImageFileService extracurricularImageFileService;
    private final ExtracurricularImageRepository extracurricularImageRepository;

    private final ExtracurricularApplyRepository extracurricularApplyRepository;
    // 비교과 프로그램 등록 신청


    public void insertExtracurricularProgram(ExtracurricularProgramFormDTO dto, String empId, MultipartFile imageFile) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new IllegalArgumentException("직원이 존재하지 않습니다."));

        ExtracurricularCategory category = categoryRepository.findById(dto.getCtgryId()).orElseThrow();

        System.out.println(imageFile);


        ExtracurricularProgram program = ExtracurricularProgram.builder()
                .employee(employee) // 사원 ID 필요
                .extracurricularCategory(category)
                .eduNm(dto.getEduNm())
                .eduType(dto.getEduType())
                .eduTrgtLmt(dto.getEduTrgtLmt())
                .eduGndrLmt(dto.getEduGndrLmt())
                .eduSlctnType(dto.getEduSlctnType())
                .eduPtcpNope(dto.getEduPtcpNope())
                .eduPrps(dto.getEduPrps())
                .eduDtlCn(dto.getEduDtlCn())
                .eduAplyBgngDt(dto.getEduAplyBgngDt())
                .eduAplyEndDt(dto.getEduAplyEndDt())
                .eduBgngYmd(dto.getEduBgngYmd())
                .eduEndYmd(dto.getEduEndYmd())
                .eduPlcNm(dto.getEduPlcNm())
                .cndCn(dto.getCndCn())
                .eduAplyDt(LocalDateTime.now())
                .sttsNm(SttsNm.REQUESTED) // 상태 ENUM
                .build();

        extracurricularProgramRepository.save(program);

        if (imageFile != null && !imageFile.isEmpty()) {
            // 1. 파일 저장 (물리 경로에 저장)
            String savedFilePath = extracurricularImageFileService.saveFile(imageFile);
            // 2. 이미지 정보 엔티티 생성
            ExtracurricularImage image = ExtracurricularImage.builder()
                    .extracurricularProgram(program)
                    .imgFilePathNm(savedFilePath)
                    .imgFileSz((int) imageFile.getSize())
                    .imgType(ImgType.PNG)
                    .build();
            // 3. 이미지 엔티티 저장 (별도의 Repository 필요)
            extracurricularImageRepository.save(image);
        }
        extracurricularScheduleService.registerSchedulesAutomatically(
                program,
                dto.getEduBgngYmd(),             // 교육 시작일
                dto.getEduEndYmd(),              // 교육 종료일
                dto.getEduDays(),                // 반복 요일 리스트
                dto.getEduStartTime(),           // 시작 시간
                dto.getEduEndTime()              // 종료 시간
        );
    }

    //비교과 프로그램 등록 요청 리스트 및 필터 조회
    public Page<ExtracurricularProgramAdminDTO> filterList(ProgramFilterRequest filter, Pageable pageable) {
        Specification<ExtracurricularProgram> spec = ExtracurricularProgramSpecification.filterBy(filter);

        Page<ExtracurricularProgram> programs = extracurricularProgramRepository.findAll(spec, pageable);

        return programs.map(program -> {
            ExtracurricularProgramAdminDTO dto = new ExtracurricularProgramAdminDTO();
            dto.setEduMngId(program.getEduMngId());
            dto.setEduNm(program.getEduNm());
            dto.setEduType(program.getEduType());
            dto.setEduTrgtLmt(program.getEduTrgtLmt());
            dto.setEduGndrLmt(program.getEduGndrLmt());
            dto.setEduSlctnType(program.getEduSlctnType());
            dto.setEduPtcpNope(program.getEduPtcpNope());
            dto.setEduPrps(program.getEduPrps());
            dto.setEduDtlCn(program.getEduDtlCn());
            dto.setEduAplyBgngDt(program.getEduAplyBgngDt());
            dto.setEduAplyEndDt(program.getEduAplyEndDt());
            dto.setEduBgngYmd(program.getEduBgngYmd());
            dto.setEduEndDt(program.getEduEndYmd());
            dto.setEduPlcNm(program.getEduPlcNm());
            dto.setEduAplyDt(program.getEduAplyDt());
            dto.setEduSttsNm(program.getSttsNm());

            dto.setEmpNo(program.getEmployee().getEmpNo());
            dto.setName(program.getEmployee().getName());

            dto.setRequest(extracurricularApplyRepository.countByExtracurricularProgram_EduMngIdAndAprySttsNm(program.getEduMngId(), AprySttsNm.APPLY));
            dto.setAccept(extracurricularApplyRepository.countByExtracurricularProgram_EduMngIdAndAprySttsNm(program.getEduMngId(), AprySttsNm.ACCEPT));

            if (program.getEmployee() != null && program.getEmployee().getSchoolSubject() != null) {
                dto.setSubjectName(program.getEmployee().getSchoolSubject().getSubjectName());
            } else {
                dto.setSubjectName(null); // 또는 빈 문자열 등 원하는 기본값
            }
            ExtracurricularImage image = extracurricularImageRepository.findExtracurricularImageByExtracurricularProgram_EduMngId(program.getEduMngId());
            if (image != null) {
                dto.setExtracurricularImageDTO(modelMapper.map(image, ExtracurricularImageDTO.class));
            } else {
                dto.setExtracurricularImageDTO(null);
            }

            List<ExtracurricularApply> applyList = extracurricularApplyRepository
                    .findByExtracurricularProgram_EduMngId(program.getEduMngId());

            List<ExtracurricularApplyDTO> dtoList = applyList.stream()
                    .map(apply -> ExtracurricularApplyDTO.builder()
                            .eduAplyId(apply.getEduAplyId())
                            .eduAplyCn(apply.getEduAplyCn())
                            .aprySttsNm(apply.getAprySttsNm())
                            .eduAplyDt(apply.getEduAplyDt())
                            .build())
                    .toList();

                dto.setApplyList(dtoList);

            return dto;
        });
    }

    //비교과 프로그램 등록 승인 및 반려 수정
    public void updateExtracurricularProgram(ExtracurricularProgramUpdateFormDTO dto) {
        ExtracurricularProgram program = extracurricularProgramRepository.findById(dto.getEduMngId()).orElseThrow();
        program.setSttsNm(dto.getSttsNm());
        program.setEduMlg(dto.getEduMlg());
        program.setSttsChgDt(LocalDateTime.now());
        extracurricularProgramRepository.save(program);
    }

    // 비교과 프로그램 삭제
    public void deleteExtracurricularProgram(Long eduMngId) {
        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다. ID: " + eduMngId));
        extracurricularProgramRepository.delete(program);
    }

    // 비교과 프로그램 자동 운영종료/ 운영중 으로  상태 변경 스케줄러
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정(00:00:00)에 실행됨
    @Transactional
    public void autoUpdateProgramStatus() {
        System.out.println("자동 운영종료/운영중 상태 변경 스케줄러 실행됨" + LocalDateTime.now());
        // 오늘 날짜 이전(과거)의 eduEndYmd(교육 종료일)를 가진 프로그램 중
        // 현재 상태가 IN_PROGRESS(운영중)인 프로그램만 조회
        List<ExtracurricularProgram> programsToEnd = extracurricularProgramRepository
                .findByEduEndYmdBeforeAndSttsNm(
                        LocalDate.now(),
                        SttsNm.IN_PROGRESS
                );
        // 조회된 모든 프로그램에 대해 상태를 ENDED(운영종료)로 변경
        for (ExtracurricularProgram program : programsToEnd) {
            program.setSttsNm(SttsNm.ENDED); // 상태 변경
            program.setSttsChgDt(LocalDateTime.now()); // 상태 변경 시간 기록
        }
        // 변경된 프로그램들을 한 번에 저장 (Batch 업데이트)
        extracurricularProgramRepository.saveAll(programsToEnd);

        List<ExtracurricularProgram> programsToStart = extracurricularProgramRepository
                .findByEduBgngYmdBeforeAndSttsNm(
                        LocalDate.now().plusDays(1),
                        SttsNm.APPROVED
                );
        for (ExtracurricularProgram program : programsToStart) {
            program.setSttsNm(SttsNm.IN_PROGRESS);
            program.setSttsChgDt(LocalDateTime.now());
        }
        extracurricularProgramRepository.saveAll(programsToStart);
    }


}
