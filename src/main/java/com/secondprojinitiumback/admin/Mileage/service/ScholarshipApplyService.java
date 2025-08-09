package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.constants.ScholarshipState;
import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import com.secondprojinitiumback.admin.Mileage.dto.*;
import com.secondprojinitiumback.admin.Mileage.repository.MileagePerfRepository;
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
    private final MileagePerfRepository mileagePerfRepository;


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

    // 3. 장학금 신청
    public void register(ScholarshipApplyRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new EntityNotFoundException("학생이 존재하지 않습니다"));
        BankAccount account = bankAccountRepository.findById(dto.getAccountNo())
                .orElseThrow(() -> new EntityNotFoundException("계좌가 존재하지 않습니다"));
        CommonCode code = codeRepository.findById(new CommonCodeId(dto.getCodeSe(), dto.getCode()))
                .orElseThrow(() -> new EntityNotFoundException("코드가 없습니다"));

        //학생의 마일리지 누계 조회
        MileageTotal total = mileageTotalRepository.findByStudent_StudentNo(dto.getStudentNo())
                .orElseThrow(() -> new EntityNotFoundException("누적 마일리지가 존재하지 않습니다"));

        //마일리지 기준 조건 체크  : 요구사항 기능 명세서에 작성할 것 (얼마 이상 이어야지 신청이 가능하다라고)
        if (total.getTotalScore() < 100) {
            throw new IllegalStateException("100점 이상의 누적 마일리지가 있어야 신청할 수 있습니다.");
        }

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

        CommonCode newStatus = codeRepository.findById(new CommonCodeId(ScholarshipState.CODE_GROUP, newCode))
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

        CommonCodeId currentStateId = apply.getStateCode() != null ? apply.getStateCode().getId() : null;

        if (!ScholarshipState.APPROVE.matches(currentStateId)) {
            throw new IllegalStateException("승인 상태가 아니면 지급할 수 없습니다.");
        }

        Student student = apply.getStudent();
        int usedMileage = apply.getAccumulatedMileage();

        // 차감 실적 등록
        MileagePerf perf = MileagePerf.builder()
                .student(student)
                .accMlg(usedMileage)
                .createdAt(LocalDateTime.now())
                .scholarshipApply(apply) // 장학금 신청과 연결
                .build();
        mileagePerfRepository.save(perf);

        // 마일리지 누계 차감
        MileageTotal total = mileageTotalRepository.findByStudent(student)
                .orElseThrow(() -> new EntityNotFoundException("누적 마일리지가 없습니다."));
        total.subtract(usedMileage);
        mileageTotalRepository.save(total);

        // 상태코드 → 지급완료로 변경
        CommonCode paidStatus = codeRepository.findById(ScholarshipState.PAYMENT.toCommonCodeId())
                .orElseThrow(() -> new EntityNotFoundException("지급 상태코드를 찾을 수 없습니다."));
        apply.setStateCode(paidStatus);

        // 지급일자/금액 설정
        apply.setApproveDate(LocalDateTime.now()); // 지급 처리일
        apply.setPaymentAmount(java.math.BigDecimal.valueOf(usedMileage * 100L)); // 예: 점수 × 100원

        // 저장은 JPA 영속성 컨텍스트에 의해 자동 처리됨
    }


    // 7. 마일리지 누계 확인
    public MileageTotalResponseDto getMileageTotal(String studentNo) {

        // 먼저 학번으로 Student 엔티티를 조회
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 없음"));

        // Student 엔티티로 MileageTotal을 조회
        MileageTotal total = mileageTotalRepository.findByStudent(student)
                .orElseThrow(() -> new EntityNotFoundException("마일리지 없음"));

        return MileageTotalResponseDto.builder()
                .studentNo(student.getStudentNo())
                .totalScore((double) total.getTotalScore())
                .build();
    }
}

