package com.secondprojinitiumback.user.student.service.serviceImpl;

import com.secondprojinitiumback.common.bank.Repository.BankAccountRepository;
import com.secondprojinitiumback.common.bank.domain.BankAccount;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.login.domain.LoginInfo;
import com.secondprojinitiumback.common.login.repository.LoginInfoRepository;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import com.secondprojinitiumback.user.student.service.serviceinterface.StudentService;
import com.secondprojinitiumback.user.student.dto.addStudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final LoginInfoRepository loginInfoRepository;
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final CommonCodeRepository commonCodeRepository;
    private final EmployeeRepository employeeRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addStudent(addStudentDto addStudentDto) {
        // 1. 학번 생성
        String studentNo = generateStudentNo(addStudentDto.getAdmissionDate(), addStudentDto.getSchoolSubjectCode());

        // 2. 로그인 정보 생성 및 저장
        LoginInfo loginInfo = createAndSaveLoginInfo(studentNo, addStudentDto.getBirthDate());

        // 3. 관련 엔티티 조회
        SchoolSubject schoolSubject = schoolSubjectRepository.findByCode(addStudentDto.getSchoolSubjectCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid School Subject Code"));

        CommonCode gender = commonCodeRepository.findByCdAndCdSe("CO0002", addStudentDto.getGender())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Gender Code"));

        Employee advisor = employeeRepository.findByEmployeeNo(addStudentDto.getAdvisorNo())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Advisor Number"));

        BankAccount bankAccount = null;
        if (addStudentDto.getBankAccountNumber() != null && !addStudentDto.getBankAccountNumber().isEmpty()) {
            bankAccount = bankAccountRepository.findByAccountNumber(addStudentDto.getBankAccountNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Bank Account Number"));
        }

        // 4. Student 엔티티 생성 및 저장
        Student student = Student.builder()
                .studentNo(studentNo)
                .loginInfo(loginInfo)
                .schoolSubject(schoolSubject)
                .bankAccount(bankAccount)
                .name(addStudentDto.getName())
                .admissionDate(addStudentDto.getAdmissionDate())
                .birthDate(addStudentDto.getBirthDate())
                .gender(gender)
                .email(addStudentDto.getEmail())
                .advisor(advisor)
                .grade(addStudentDto.getGrade())
                .build();

        studentRepository.save(student);
    }

    private String generateStudentNo(LocalDate admissionDate, String schoolSubjectCode) {
        // 입학 연도 4자리
        String admissionYear = String.valueOf(admissionDate.getYear());

        // 학과 코드 3자리
        String departmentCode = schoolSubjectCode;

        // 학번 시퀀스 3자리
        Optional<String> lastStudentNo = studentRepository.findTopByStudentNoStartingWithAndStudentNoContainingOrderByStudentNoDesc(
                admissionYear, departmentCode);

        // 시퀀스 번호 계산
        int sequence = 1;
        // 만약 마지막 학번이 존재한다면
        if (lastStudentNo.isPresent()) {
            // 마지막 학번에서 시퀀스 번호 추출
            String lastSeqStr = lastStudentNo.get().substring(lastStudentNo.get().length() - 3);
            sequence = Integer.parseInt(lastSeqStr) + 1;
        }

        // 시퀀스 번호를 3자리로 포맷팅
        return String.format("%s%s%03d", admissionYear, departmentCode, sequence);
    }

    private LoginInfo createAndSaveLoginInfo(String loginId, LocalDate birthDate) {
        // 비밀번호는 생년월일을 YYYYMMDD 형식으로 사용
        String rawPassword = birthDate.toString().replace("-", "");
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 로그인 정보 생성
        LoginInfo loginInfo = LoginInfo.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .userType("S") // 학생 타입으로 설정
                .build();

        return loginInfoRepository.save(loginInfo);
    }
}
