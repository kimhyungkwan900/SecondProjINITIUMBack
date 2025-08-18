package com.secondprojinitiumback.user.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.constants.ScholarshipState;
import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import com.secondprojinitiumback.admin.Mileage.repository.MileageTotalRepository;
import com.secondprojinitiumback.admin.Mileage.repository.ScholarshipApplyRepository;
import com.secondprojinitiumback.common.bank.Repository.BankAccountRepository;
import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipApplyRequestDto;
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipUserInfoDto;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserScholarshipApplyService {

    // 정책 상수 (원하면 여기 숫자만 바꾸면 됨)
    private static final int MIN_MILEAGE_FOR_APPLY = 60; //최소 신청 점수

    private final StudentRepository studentRepository;
    private final MileageTotalRepository mileageTotalRepository;
    private final ScholarshipApplyRepository scholarshipApplyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CommonCodeRepository codeRepository;

    // 은행 목록 조회(공통코드 BK0001)
    public List<UserScholarshipUserInfoDto.BankItem> getBankCodes() {
        return codeRepository
                .findAllById_CodeGroupAndUseYnOrderBySortOrderAscCodeNameAsc("BK0001", "Y")
                .stream()
                .map(c -> new UserScholarshipUserInfoDto.BankItem(
                        c.getId().getCode(),
                        c.getCodeName()
                ))
                .toList();
    }

    // 사용자 정보 조회
    public UserScholarshipUserInfoDto getUserInfo(String studentNoRaw) {

        final String studentNo = studentNoRaw.trim();

        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

        MileageTotal total = mileageTotalRepository.findByStudent(student).orElse(null);
        int totalScore = (total != null) ? (int) total.getTotalScore() : 0;

        BankAccount account = bankAccountRepository
                .findFirstByOwnerIdOrderByAccountNoDesc(student.getStudentNo())
                .orElse(null);
        String accountNo = (account != null) ? account.getAccountNo() : null;

        String bankCode = (account != null && account.getBankCode()!=null)
                ? account.getBankCode().getId().getCode() : null;

        String bankName = (account != null && account.getBankCode()!=null)
                ? account.getBankCode().getCodeName() : null;

        List<UserScholarshipUserInfoDto.BankItem> banks = getBankCodes();

        return UserScholarshipUserInfoDto.builder()
                .name(student.getName())
                .studentNo(student.getStudentNo())
                .subjectName(student.getSchoolSubject() != null ? student.getSchoolSubject().getSubjectName() : "")
                .totalScore(totalScore)
                .accountNo(accountNo)
                .bankCode(bankCode)
                .bankName(bankName)
                .banks(banks)
                .build();
    }

    // 장학금 신청
    @Transactional
    public void apply(UserScholarshipApplyRequestDto dto) {

        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));
        MileageTotal total = mileageTotalRepository.findByStudent(student)
                .orElseThrow(() -> new CustomException(ErrorCode.INSUFFICIENT_MILEAGE_SCORE));

        // 1. 최소 신청 점수 체크 (60점)
        if (total.getTotalScore() < 50) {
            throw new CustomException(ErrorCode.INSUFFICIENT_MILEAGE_SCORE);
        }

        // 2. 계좌 유효성 체크
        bankAccountRepository.findById(dto.getAccountNo())
                .orElseThrow(() -> new CustomException(ErrorCode.BANK_ACCOUNT_NOT_FOUND));

        // 3. 상태 코드 "신청" APPLY
        CommonCode applyState = codeRepository.findById(ScholarshipState.APPLY.toCommonCodeId())
                .orElseThrow(() -> new CustomException(ErrorCode.COMMON_CODE_NOT_FOUND));

        // 신청 엔티티 생성 (현 시점의 총점 기준으로 신청 마일리지 기록)
        ScholarshipApply apply = ScholarshipApply.builder()
                .student(student)
                .bankAccount(bankAccountRepository.getReferenceById(dto.getAccountNo()))
                .stateCode(applyState)
                .applyDate(LocalDateTime.now())
                .accumulatedMileage((int) total.getTotalScore())
                .build();

        scholarshipApplyRepository.save(apply);
    }
}
