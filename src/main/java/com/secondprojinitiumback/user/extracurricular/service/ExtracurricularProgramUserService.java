package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularCategory;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularImage;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularImageDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularScheduleDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.*;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularImageFileService;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.dto.EmployeeDto;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularCompletion;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.dto.AppliedExtracurricularProgramDTO;
import com.secondprojinitiumback.user.extracurricular.dto.ApplyProgramDTO;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularProgramDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularCompletionRepository;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import com.secondprojinitiumback.user.extracurricular.repository.specification.ExtracurricularUserProgramSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtracurricularProgramUserService {

    private final ExtracurricularProgramRepository extracurricularProgramRepository;
    private final ExtracurricularImageRepository extracurricularImageRepository;
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final ExtracurricularApplyRepository extracurricularApplyRepository;
    private final ModelMapper modelMapper;
    private final ExtracurricularScheduleRepository extracurricularScheduleRepository;
    private final ExtracurricularAttendanceRepository extracurricularAttendanceRepository;
    private final ExtracurricularSurveyResponseRepository extracurricularSurveyResponseRepository;
    private final ExtracurricularCompletionRepository extracurricularCompletionRepository;


    public Page<ExtracurricularProgramDTO> findByFilters(
            List<Integer> competencyIds,
            String keyword,
            String statusFilter,  // 상태 필터 추가
            Pageable pageable) {

        Specification<ExtracurricularProgram> spec = Specification.where(null);

        // 기본 상태 필터: REQUESTED 제외
        spec = spec.and(ExtracurricularUserProgramSpecification.sttsNmNotRequested());
        // 역량 필터
        if (competencyIds != null && !competencyIds.isEmpty()) {
            spec = spec.and(ExtracurricularUserProgramSpecification.hasAnyCompetencyId(competencyIds));
        }
        // 키워드 필터
        if (keyword != null && !keyword.trim().isEmpty()) {
            spec = spec.and(ExtracurricularUserProgramSpecification.hasKeyword(keyword));
        }
        // 날짜 비교를 위해 현재 시간 얻기
        LocalDateTime now = LocalDateTime.now();

        // statusFilter에 따라 추가 조건
        if ("available".equalsIgnoreCase(statusFilter)) {
            // 신청 마감일이 현재보다 미래인 (신청 가능한) 프로그램
            spec = spec.and((root, query, builder) -> builder.and(
                    builder.lessThanOrEqualTo(root.get("eduAplyBgngDt"), now),
                    builder.greaterThanOrEqualTo(root.get("eduAplyEndDt"), now)
            ));
        } else if ("past".equalsIgnoreCase(statusFilter)) {
            // 신청 마감일이 현재보다 과거인 (신청 마감된) 프로그램
            spec = spec.and((root, query, builder) -> builder.lessThan(root.get("eduAplyEndDt"), now));
        } else if ("close".equalsIgnoreCase(statusFilter)) {
            // 신청 마감일이 현재 이후이고, 가까운 순서로 정렬
            spec = spec.and((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("eduAplyEndDt"), now));
            // 정렬을 마감일 오름차순으로 변경
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("eduAplyEndDt").ascending());
        } else if ("all".equalsIgnoreCase(statusFilter)) {
            // 모든 프로그램, 추가 필터 없음
        }

        // 쿼리 실행
        Page<ExtracurricularProgram> entityPage = extracurricularProgramRepository.findAll(spec, pageable);

        // DTO 변환
        List<ExtracurricularProgramDTO> dtoList = entityPage.stream()
                .map(program -> {
                    ExtracurricularProgramDTO dto = modelMapper.map(program, ExtracurricularProgramDTO.class);

                    List<ExtracurricularImage> imageList = extracurricularImageRepository.findByExtracurricularProgram_EduMngId(program.getEduMngId());
                    List<ExtracurricularImageDTO> imageDTOList = imageList.stream()
                            .map(image -> modelMapper.map(image, ExtracurricularImageDTO.class))
                            .toList();

                    dto.setExtracurricularImageDTO(imageDTOList);

                    dto.setAccept(extracurricularApplyRepository.countByExtracurricularProgram_EduMngIdAndAprySttsNm(program.getEduMngId(), AprySttsNm.ACCEPT));

                    dto.setCtgryNm(program.getExtracurricularCategory().getCtgryNm());
                    return dto;
                })
                .toList();

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    // 프로그램 상세 페이지 반환
    public ExtracurricularProgramDTO findProgramByEduMngId(Long eduMngId) {
        // 프로그램 엔티티 조회
        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId)
                .orElseThrow(() -> new EntityNotFoundException("해당 프로그램을 찾을 수 없습니다."));

        // DTO 생성 및 필드 설정
        ExtracurricularProgramDTO dto = new ExtracurricularProgramDTO();

        dto.setEduMngId(program.getEduMngId());
        dto.setEduNm(program.getEduNm());
        dto.setEduDtlCn(program.getEduDtlCn());
        dto.setEduTrgtLmt(program.getEduTrgtLmt());
        dto.setEduGndrLmt(program.getEduGndrLmt());
        dto.setEduAplyBgngDt(program.getEduAplyBgngDt());
        dto.setEduAplyEndDt(program.getEduAplyEndDt());
        dto.setEduBgngYmd(program.getEduBgngYmd());
        dto.setEduEndYmd(program.getEduEndYmd());
        dto.setEduPtcpNope(program.getEduPtcpNope());
        dto.setEduType(program.getEduType());
        dto.setEduMlg(program.getEduMlg());
        dto.setCtgryNm(program.getExtracurricularCategory().getCtgryNm());
        dto.setEduPrps(program.getEduPrps());
        dto.setCndCn(program.getCndCn());
        dto.setName(program.getEmployee().getName());
        dto.setEmail(program.getEmployee().getEmail());
        dto.setEduPlcNm(program.getEduPlcNm());

        dto.setAccept(extracurricularApplyRepository.countByExtracurricularProgram_EduMngIdAndAprySttsNm(program.getEduMngId(), AprySttsNm.ACCEPT));

        String subjectCode = program.getEmployee().getSchoolSubject().getSubjectCode();
        SchoolSubject schoolSubject = schoolSubjectRepository.findBySubjectCode(subjectCode).orElseThrow();
        if (schoolSubject != null) {
            dto.setSubjectName(schoolSubject.getSubjectName());  // 학과 이름 세팅
        } else {
            dto.setSubjectName("학과 정보 없음");
        }
        // 이미지 조회 및 DTO 변환
        List<ExtracurricularImage> images =
                extracurricularImageRepository.findByExtracurricularProgram_EduMngId(eduMngId);

        List<ExtracurricularImageDTO> imageDTOs = images.stream()
                .map(image -> {
                    ExtracurricularImageDTO dtoImg = new ExtracurricularImageDTO();
                    dtoImg.setImgId(image.getImgId());
                    dtoImg.setImgFilePathNm(image.getImgFilePathNm());
                    return dtoImg;
                })
                .collect(Collectors.toList());
        dto.setExtracurricularImageDTO(imageDTOs);

        List<ExtracurricularSchedule> schedules = extracurricularScheduleRepository
                .findByExtracurricularProgram_EduMngId(eduMngId);
        List<ExtracurricularScheduleDTO> scheduleDTOs = schedules.stream()
                .map(schedule -> {
                    ExtracurricularScheduleDTO scheduleDTO = new ExtracurricularScheduleDTO();
                    scheduleDTO.setEduShdlId(schedule.getEduShdlId());
                    scheduleDTO.setEduDt(schedule.getEduDt());
                    scheduleDTO.setEduEndTm(schedule.getEduEdnTm());  // 필드명이 DTO와 맞게 eduEndTm으로!
                    return scheduleDTO;
                })
                .collect(Collectors.toList());

        dto.setExtracurricularSchedules(scheduleDTOs);


        return dto;
    }

    public Page<AppliedExtracurricularProgramDTO> getAppliedPrograms(String userId, Pageable pageable) {
        // 학생의 신청 내역을 페이징 처리하여 조회
        Page<ExtracurricularApply> applies = extracurricularApplyRepository.findByStudent_StudentNo(userId, pageable);
        // 만약 신청 내역이 없다면 빈 페이지 반환
        List<AppliedExtracurricularProgramDTO> dtoList = applies.getContent().stream()
                .map(apply -> {
                    ExtracurricularProgram program = apply.getExtracurricularProgram();
                    return AppliedExtracurricularProgramDTO.builder()
                            .eduMngId(program.getEduMngId())
                            .eduNm(program.getEduNm())
                            .ctgryNm(program.getExtracurricularCategory().getCtgryNm())
                            .eduAplyBgngDt(program.getEduAplyBgngDt())
                            .eduAplyEndDt(program.getEduAplyEndDt())
                            .aprySttsNm(apply.getAprySttsNm())
                            .build();
                })
                .collect(Collectors.toList());
        // 만약 신청 내역이 없다면 빈 리스트를 반환
        return new PageImpl<>(dtoList, pageable, applies.getTotalElements());
    }

    public Page<ApplyProgramDTO> getApplyPrograms(
            String stdfntNo,
            String eduFnshYn,
            String keyword,
            Pageable pageable) {

        Specification<ExtracurricularApply> spec = Specification.where(
                (root, query, cb) -> cb.equal(root.get("student").get("studentNo"), stdfntNo)
        );

        spec = spec.and((root, query, cb) -> cb.equal(root.get("delYn"), "N"));

        if (eduFnshYn != null && !eduFnshYn.isBlank()) {
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> completionJoin = root.join("extracurricularCompletion", JoinType.LEFT);
                if ("N".equalsIgnoreCase(eduFnshYn)) {
                    return cb.or(
                            cb.isNull(completionJoin.get("eduFnshYn")),
                            cb.equal(completionJoin.get("eduFnshYn"), "N")
                    );
                } else if ("Y".equalsIgnoreCase(eduFnshYn)) {
                    return cb.equal(completionJoin.get("eduFnshYn"), "Y");
                }
                return cb.conjunction(); // eduFnshYn 값이 N,Y 외에는 조건 안 걸기
            });
        }

        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("extracurricularProgram").get("eduNm"), "%" + keyword + "%")
            );
        }

        Page<ExtracurricularApply> applies = extracurricularApplyRepository.findAll(spec, pageable);


        // 엔티티 -> DTO 매핑
        return applies.map(apply -> {
            ExtracurricularProgram program = apply.getExtracurricularProgram();

            int totalAttendance = extracurricularScheduleRepository
                    .countExtracurricularSchedulesByExtracurricularProgram_EduMngId(program.getEduMngId());
            System.out.println("totalAttendance : "+totalAttendance);
            int myAttendance = extracurricularAttendanceRepository
                    .countByExtracurricularSchedule_ExtracurricularProgram_EduMngIdAndAtndcYn(
                            program.getEduMngId(), "Y"
                    );
            System.out.println("myAttendance : "+myAttendance);
            double attendanceRate = 0.0;

            if (totalAttendance > 0) {
                attendanceRate = (double) myAttendance / totalAttendance *100;
            }

            Boolean surveyYn = extracurricularSurveyResponseRepository.existsByEduMngIdAndStudent(program.getEduMngId() , apply.getStudent().getStudentNo());
            System.out.println("surveyYn : " + surveyYn);

            Optional<ExtracurricularCompletion> completionOpt = extracurricularCompletionRepository
                    .findExtracurricularCompletionByExtracurricularApply_EduAplyId(apply.getEduAplyId());

            String eduFnshYnValue = null;
            if (completionOpt.isPresent()) {
                eduFnshYnValue = completionOpt.get().getEduFnshYn();
            }

            return ApplyProgramDTO.builder()
                    .eduAplyId(apply.getEduAplyId())
                    .eduNm(program.getEduNm())
                    .eduMngId(program.getEduMngId())
                    .eduEndYmd(program.getEduEndYmd())
                    .cndCn(program.getCndCn())
                    .attendance(attendanceRate)
                    .surveyYn(surveyYn)
//                    .eduFnshId(apply.getEduFnshId())
                    .eduFnshYn(eduFnshYnValue)
                    .build();
        });
    }

}