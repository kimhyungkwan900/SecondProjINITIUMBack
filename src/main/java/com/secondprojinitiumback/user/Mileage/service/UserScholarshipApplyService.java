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
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipApplyRequestDto;
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipUserInfoDto;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserScholarshipApplyService {

    private final StudentRepository studentRepository;
    private final MileageTotalRepository mileageTotalRepository;
    private final ScholarshipApplyRepository scholarshipApplyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CommonCodeRepository codeRepository;


    // 1. 사용자 정보 조회 (이름, 학번, 과목명, 총점수, 계좌번호)
    public UserScholarshipUserInfoDto getUserInfo(String studentNo) {

        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생이 존재하지 않습니다."));

        MileageTotal total = mileageTotalRepository.findByStudent(student)
                .orElseThrow(() -> new EntityNotFoundException("마일리지 누계가 없습니다."));

        String accountNo = bankAccountRepository.findByOwnerId(student.getStudentNo())
                .map(BankAccount::getAccountNo)
                .orElse(null);

        //사용자 정보, 누적 점수 dto로 변환
        return UserScholarshipUserInfoDto.builder()
                .name(student.getName())
                .studentNo(student.getStudentNo())
                .subjectName(student.getSchoolSubject().getSubjectName())
                .totalScore(total.getTotalScore())
                .accountNo(accountNo)
                .build();
    }

    // 2. 장학금 신청 처리
    public void apply(UserScholarshipApplyRequestDto dto) {

        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new EntityNotFoundException("학생이 존재하지 않습니다."));
        MileageTotal total = mileageTotalRepository.findByStudent(student)
                .orElseThrow(() -> new EntityNotFoundException("마일리지 누계가 없습니다."));

        //누적 점수가 50점 미만이면 예외 발생 → 신청 불가
        if (total.getTotalScore() < 50) {
            throw new IllegalStateException("총 마일리지가 50점 이상일 경우에만 신청이 가능합니다.");
        }

        // 계좌 유효성 체크
        bankAccountRepository.findById(dto.getAccountNo())
                .orElseThrow(() -> new EntityNotFoundException("계좌가 존재하지 않습니다."));

        // 상태 코드 "신청" APPLY 가져오기
        CommonCode applyState = codeRepository.findById(ScholarshipState.APPLY.toCommonCodeId())
                .orElseThrow(() -> new EntityNotFoundException("신청 상태 코드 없음"));

        // 신청 엔티티 생성
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

