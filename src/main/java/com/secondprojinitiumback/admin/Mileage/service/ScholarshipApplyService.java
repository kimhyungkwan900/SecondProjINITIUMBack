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
import org.springframework.transaction.annotation.Transactional;

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


    private static final int  MIN_MILEAGE = 60;        // 최소 신청 요건 (누적 마일리지 점수)
    private static final long MONEY_RATE  = 100L;      // 1점당 금액(원)
    private static final long MAX_PAYOUT  = 500_000L;  // 학생별 총 지급  - 전체기간 기준

    // 1. 전체 목록 조회 (검색 + 페이징)
    public PageResponseDto<ScholarshipApplyResponseDto> getList(PageRequestDto requestDto,
                                                                String studentName,
                                                                String studentNo,
                                                                String subjectName,
                                                                String stateCode) {
        Pageable pageable = requestDto.toPageable();

        Page<ScholarshipApply> page = repository.searchWithPaging(
                studentNo, studentName, subjectName, stateCode, pageable
        );

        List<ScholarshipApplyResponseDto> dtoList = page.getContent().stream()
                .map(ScholarshipApplyResponseDto::from)
                .toList();

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
    @Transactional
    public void register(ScholarshipApplyRequestDto dto) {
        final String stdNo = dto.getStudentNo().trim();

        Student student = studentRepository.findById(stdNo)
                .orElseThrow(() -> new EntityNotFoundException("학생이 존재하지 않습니다."));
        BankAccount account = bankAccountRepository.findById(dto.getAccountNo())
                .orElseThrow(() -> new EntityNotFoundException("계좌가 존재하지 않습니다."));

        // 현재 누적 마일리지
        MileageTotal total = mileageTotalRepository.findByStudent_StudentNo(stdNo)
                .orElseThrow(() -> new EntityNotFoundException("누적 마일리지가 존재하지 않습니다."));

        if (total.getTotalScore() < MIN_MILEAGE) {
            throw new IllegalStateException(MIN_MILEAGE + "점 이상의 누적 마일리지가 있어야 신청할 수 있습니다.");
        }

        // 상태는 무조건 '신청(APPLY)'
        CommonCode applyCode = codeRepository.findById(ScholarshipState.APPLY.toCommonCodeId())
                .orElseThrow(() -> new EntityNotFoundException("상태코드(APPLY)를 찾을 수 없습니다."));

        ScholarshipApply apply = ScholarshipApply.builder()
                .student(student)
                .bankAccount(account)
                .stateCode(applyCode)
                .accumulatedMileage(total.getTotalScore())
                .applyDate(LocalDateTime.now())
                .build();

        repository.save(apply);
    }

    // 4. 상태 변경
    @Transactional
    public void updateStatus(Long id, String newCode) {
        ScholarshipApply apply = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 내역이 존재하지 않습니다."));

        CommonCode newStatus = codeRepository.findById(new CommonCodeId(ScholarshipState.CODE_GROUP, newCode))
                .orElseThrow(() -> new EntityNotFoundException("상태코드를 찾을 수 없습니다."));

        apply.setStateCode(newStatus);
    }

    // 5. 반려 사유 입력
    @Transactional
    public void updateRejectReason(Long id, String reason) {
        ScholarshipApply apply = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 내역이 존재하지 않습니다."));
        apply.setRejectReason(reason);
    }

    // 6. 지급 처리 (승인 상태만 가능 + 학생별 누적 지급 상한 체크)
    @Transactional
    public void processPayment(Long id) {
        ScholarshipApply apply = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 없음"));

        CommonCodeId currentStateId = apply.getStateCode() != null ? apply.getStateCode().getId() : null;
        if (!ScholarshipState.APPROVE.matches(currentStateId)) {
            throw new IllegalStateException("승인 상태가 아니면 지급할 수 없습니다.");
        }

        // 이번 지급 예정 금액 = (신청 당시 누적점수) × 환산율
        long baseMileage   = apply.getAccumulatedMileage();
        long currentAmount = baseMileage * MONEY_RATE;

        // 학생 누적 지급합(지급완료 건)
        long alreadyPaid = repository
                .sumPaidAmountByStudent(apply.getStudent().getStudentNo(), ScholarshipState.PAYMENT.getCode())
                .longValue();

        if (alreadyPaid + currentAmount > MAX_PAYOUT) {
            throw new IllegalStateException(
                    "지급 상한(" + MAX_PAYOUT + "원) 초과: 현재 누적 " + alreadyPaid + "원, 이번 " + currentAmount + "원");
        }

        //마일리지 차감 실적 기록: 음수로 저장
        Student student = apply.getStudent();

        MileagePerf perf = MileagePerf.builder()
                .student(student)
                .accMlg(-(int) baseMileage)         // 차감은 음수로 기록
                .createdAt(LocalDateTime.now())
                .scholarshipApply(apply)            // 연동
                .build();
        mileagePerfRepository.save(perf);

        // 누계 차감
        MileageTotal total = mileageTotalRepository.findByStudent(student)
                .orElseThrow(() -> new EntityNotFoundException("누적 마일리지가 없습니다."));
        total.subtract((int) baseMileage);

        // 상태코드 → 지급완료
        CommonCode paidStatus = codeRepository.findById(ScholarshipState.PAYMENT.toCommonCodeId())
                .orElseThrow(() -> new EntityNotFoundException("지급 상태코드를 찾을 수 없습니다."));
        apply.setStateCode(paidStatus);

        // 지급일/금액 설정
        apply.setApproveDate(LocalDateTime.now()); // 지급 처리일
        apply.setPaymentAmount(java.math.BigDecimal.valueOf(currentAmount));
    }

    // 7. 마일리지 누계 확인
    public MileageTotalResponseDto getMileageTotal(String studentNo) {
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 없음"));

        MileageTotal total = mileageTotalRepository.findByStudent(student)
                .orElseThrow(() -> new EntityNotFoundException("마일리지 없음"));

        return MileageTotalResponseDto.builder()
                .studentNo(student.getStudentNo())
                .totalScore((double) total.getTotalScore())
                .build();
    }
}
