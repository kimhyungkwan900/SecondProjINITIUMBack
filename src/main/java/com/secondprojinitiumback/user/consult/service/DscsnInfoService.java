package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnResultDto;
import com.secondprojinitiumback.user.consult.dto.common.StudentDto;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnInfoRequestDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnApplyResponseDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnInfoResponseDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnScheduleResponseDto;
import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DscsnInfoService {
    private final DscsnInfoRepository dscsnInfoRepository;

    //--- 상담정보 생성
    public void createDscsnInfo(DscsnApply dscsnApply) {

        //상담정보 ID 생성
        //1. 상담종류 키워드 가져오기
        String prefix = dscsnApply.getDscsnApplyId().charAt(0) + "I";

        //2. 시퀀스 번호 생성
        String seqNum = getNextInfoSequence(prefix);

        //3. ID 생성
        String dscsnInfoId = prefix + seqNum; // I는 상담정보를 나타내는 접두사

        //상담정보 엔티티 생성
        DscsnInfo dscsnInfo = DscsnInfo.builder()
                .dscsnInfoId(dscsnInfoId)
                .dscsnStatus("예약대기") // 초기 상태 설정
                .dscsnResultCn(null) // 상담 결과 내용 초기값
                .dscsnReleaseYn("N") // 상담 결과 공개 여부 초기값
                .dscsnApply(dscsnApply) // 상담 신청 ID
                .build();

        //상담정보 저장
        dscsnInfoRepository.save(dscsnInfo);
    }

    //--- 학생 상담내역 조회
    @Transactional(readOnly = true)
    public Page<DscsnInfoResponseDto> getDscsnInfo(DscsnInfoSearchDto dscsnInfoSearchDto, Pageable pageable) {

        // 검색 조건에 따라 상담정보 페이지 조회
        Page<DscsnInfo> dscsnInfoPage =
                dscsnInfoRepository.getDscsnInfoPageByCondition(dscsnInfoSearchDto, pageable);

        // 상담정보를 DscsnInfoResponseDto로 변환하여 반환
        return dscsnInfoPage.map(this::toResponseDtoSafe);
    }

    //--- 상담상태 변경
    public void updateDscsnStatus(String dscsnInfoId, String status) {

        // 상담정보 가져오기
        DscsnInfo dscsnInfo = dscsnInfoRepository.findById(dscsnInfoId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_INFO_NOT_FOUND));

        // 상담 상태 업데이트
        dscsnInfo.updateDscsnStatus(status);
    }

    //--- 상담결과 등록
    public void registerDscsnResult(DscsnResultDto dscsnResultDto) {

        // 상담정보 가져오기
        DscsnInfo dscsnInfo = dscsnInfoRepository.findById(dscsnResultDto.getDscsnInfoId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_INFO_NOT_FOUND));

        // 상담 결과 공개여부 등록
        dscsnInfo.updateDscsnReleaseYn(dscsnResultDto.getReleaseYn());

        // 상담 결과 등록
        dscsnInfo.updateDscsnResultCn(dscsnResultDto.getResult());

        // 상담 상태 업데이트
        dscsnInfo.updateDscsnStatus("상담완료");
    }

    //시퀀스 번호 생성 메소드
    public String getNextInfoSequence(String prefix) {
        DscsnInfo lastDscsnInfo = dscsnInfoRepository.findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc(prefix);

        if(lastDscsnInfo == null) {
            return String.format("%04d", 1);
        }
        else{
            String lastId = lastDscsnInfo.getDscsnInfoId();
            String seqPart = lastId.substring(prefix.length()); // 접두사 이후 부분 추출
            int seqNum = Integer.parseInt(seqPart); // 문자열을 정수로 변환
            seqNum++; // 시퀀스 번호 증가
            return String.format("%04d", seqNum); // 4자리 문자열로 포맷팅
        }
    }

    private DscsnInfoResponseDto toResponseDtoSafe(DscsnInfo e) {
        // null-safe 로컬 변수
        var apply = e.getDscsnApply();
        var student = (apply != null) ? apply.getStudent() : null;
        var dt = (apply != null) ? apply.getDscsnDt() : null;
        var emp = (dt != null) ? dt.getEmployee() : null;
        var kind = (apply != null) ? apply.getDscsnKind() : null;

        return DscsnInfoResponseDto.builder()
                .dscsnInfoId(e.getDscsnInfoId())
                .dscsnStatus(e.getDscsnStatus())
                .dscsnResultCn(e.getDscsnResultCn())
                .dscsnReleaseYn(e.getDscsnReleaseYn())

                .dscsnApplyDto(
                        (apply == null) ? null :
                                DscsnApplyResponseDto.builder()
                                        .dscsnApplyId(apply.getDscsnApplyId())
                                        .studentTelno(apply.getStudentTelno())
                                        .dscsnApplyCn(apply.getDscsnApplyCn())

                                        .studentDto(
                                                (student == null) ? null :
                                                        StudentDto.builder()
                                                                .studentNo(student.getStudentNo())
                                                                .schoolSubject(student.getSchoolSubject().getSubjectName())
                                                                .name(student.getName())
                                                                .email(student.getEmail())
                                                                .build()
                                        )

                                        .dscsnScheduleDto(
                                                (dt == null) ? null :
                                                        DscsnScheduleResponseDto.builder()
                                                                .scheduleDate(dt.getPossibleDate())
                                                                .startTime(dt.getPossibleTime())
                                                                .empNo( (emp != null) ? emp.getEmpNo() : null )
                                                                .empName( (emp != null) ? emp.getName() : null )
                                                                .schoolSubject( (emp != null && emp.getSchoolSubject() != null)
                                                                        ? emp.getSchoolSubject().getSubjectName()
                                                                        : null )
                                                                .build()
                                        )

                                        .dscsnKindDto(
                                                (kind == null) ? null :
                                                        DscsnKindDto.builder()
                                                                .dscsnKindName(kind.getDscsnKindName())
                                                                .dscsnTypeName(kind.getDscsnTypeName())
                                                                .build()
                                        )
                                        .build()
                )
                .build();
    }
}

