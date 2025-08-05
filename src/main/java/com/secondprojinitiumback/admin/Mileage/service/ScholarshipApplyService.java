package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import com.secondprojinitiumback.admin.Mileage.dto.*;
import com.secondprojinitiumback.admin.Mileage.repository.MileageTotalRepository;
import com.secondprojinitiumback.admin.Mileage.repository.ScholarshipApplyRepository;
import com.secondprojinitiumback.common.bank.Repository.BankAccountRepository;
import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.CommonCodeId;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipApplyService {

    private final ScholarshipApplyRepository repository;
    private final StudentRepository studentRepository;
    private final MileageTotalRepository mileageTotalRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CommonCodeRepository codeRepository;

    //1. 전체 목록 조회 (검색 + 페이징)
    public PageResponseDto<ScholarshipApplyResponseDto> getList(PageRequestDto requestDto,
                                                                String studentName,
                                                                String studentNo,
                                                                String subjectName,
                                                                String stateCode) {
        Pageable pageable = requestDto.toPageable();

        // DB에서 조건에 맞는 페이지 데이터 가져오기
        Page<ScholarshipApply> page = repository.searchWithPaging(
                studentNo, studentName, subjectName, stateCode, pageable
        );

        // Entity → DTO 변환
        List<ScholarshipApplyResponseDto> dtoList = page.getContent().stream()
                .map(ScholarshipApplyResponseDto::from)
                .toList();

        // DTO 목록 + 페이징 정보로 감싸서 리턴
        return PageResponseDto.<ScholarshipApplyResponseDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(requestDto)
                .totalCount(page.getTotalElements())
                .build();
    }

    // 2. 신청 상세 조회
    public ScholarshipApplyResponseDto getDetail(Long id) {
        ScholarshipApply apply = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 신청이 존재하지 않습니다."));

        return ScholarshipApplyResponseDto.from(apply);
    }

    // 3. 신청 등록
    public void register(ScholarshipApplyRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new EntityNotFoundException("학생 정보 없음"));
        BankAccount account = bankAccountRepository.findById(dto.getAccountNo())
                .orElseThrow(() -> new EntityNotFoundException("계좌 정보 없음"));
        CommonCode code = codeRepository.findById(new CommonCodeId(dto.getCodeSe(), dto.getCode()))
                .orElseThrow(() -> new EntityNotFoundException("코드 정보 없음"));

        ScholarshipApply apply = ScholarshipApply.builder()
                .student(student)
                .bankAccount(account)
                .stateCode(code)
                .accumulatedMileage(dto.getMileageScore().intValue())
                .applyDate(LocalDateTime.now())
                .build();

        repository.save(apply);
    }

    // 4. 상태 변경
    public void updateStatus(Long id, String newCode) {
        ScholarshipApply apply = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 내역이 존재하지 않습니다"));

        CommonCode newStatus = codeRepository.findById(new CommonCodeId("ML001", newCode))
                .orElseThrow(() -> new EntityNotFoundException("코드 없음"));

        apply.setStateCode(newStatus);
    }

    // 5. 반려 사유 입력
    public void updateRejectReason(Long id, String reason) {
        ScholarshipApply apply = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 내역이 존재하지 않습니다"));

        apply.setRejectReason(reason);
    }

    // 6. 지급 처리
    public void processPayment(Long id) {
        ScholarshipApply apply = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 없음"));

        //현재 신청 상태가 '승인' (2) 가 아닌 경우 지급 불가
        if (apply.getStateCode() == null ||
                apply.getStateCode().getId() == null ||
                !"2".equals(apply.getStateCode().getId().getCode())) {

            throw new IllegalStateException("승인 상태가 아니면 지급할 수 없습니다.");
        }
    }

    // 7. 마일리지 누계 확인
    public MileageTotalResponseDto getMileageTotal(String studentNo) {

        // 먼저 학번으로 Student 엔티티를 조회
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 없음"));

        // Student 엔티티로 MileageTotal을 조회
        MileageTotal total = mileageTotalRepository.findById(student)
                .orElseThrow(() -> new EntityNotFoundException("마일리지 없음"));

        return MileageTotalResponseDto.builder()
                .studentNo(studentNo)
                .totalScore(total.getTotalScore())
                .build();
    }
}

