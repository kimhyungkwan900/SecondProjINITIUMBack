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
import com.secondprojinitiumback.user.student.constant.StudentStatus;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.domain.StudentStatusInfo;
import com.secondprojinitiumback.user.student.dto.*;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import com.secondprojinitiumback.user.student.repository.StudentStatusInfoRepository;
import com.secondprojinitiumback.user.student.service.serviceinterface.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final StudentStatusInfoRepository studentStatusInfoRepository;

    // 학생 입학 (최초 등록)
    @Override
    public void enrollStudent(EnrollStudentDto enrollStudentDto) {
        // 학번 생성
        String studentNo = generateStudentNo(enrollStudentDto.getAdmissionDate(), enrollStudentDto.getSchoolSubjectCode());
        // 로그인 정보 생성 및 저장
        LoginInfo loginInfo = createAndSaveLoginInfo(studentNo, enrollStudentDto.getBirthDate());
        // 학과 코드 조회
        SchoolSubject schoolSubject = schoolSubjectRepository.findByCode(enrollStudentDto.getSchoolSubjectCode())
                .orElseThrow(() -> new IllegalArgumentException("유효하지않은 학과 코드"));
        // 성별 코드 조회
        CommonCode gender = commonCodeRepository.findByCdAndCdSe(enrollStudentDto.getGender(),"CO0002")
                .orElseThrow(() -> new IllegalArgumentException("유효하지않은 성별 코드"));
        // 지도교수 정보 조회
        Employee advisor = employeeRepository.findByEmployeeNo(enrollStudentDto.getAdvisorNo())
                .orElseThrow(() -> new IllegalArgumentException("유효하지않은 지도교수 번호"));
        // 계좌번호 조회
        BankAccount bankAccount = null;
        if (enrollStudentDto.getBankAccountNumber() != null && !enrollStudentDto.getBankAccountNumber().isEmpty()) {
            bankAccount = bankAccountRepository.findByAccountNumber(enrollStudentDto.getBankAccountNumber())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지않은 계좌번호"));
        }
        // 학생Entity 생성
        Student student = Student.builder()
                .studentNo(studentNo)
                .loginInfo(loginInfo)
                .schoolSubject(schoolSubject)
                .bankAccount(bankAccount)
                .name(enrollStudentDto.getName())
                .admissionDate(enrollStudentDto.getAdmissionDate())
                .birthDate(enrollStudentDto.getBirthDate())
                .gender(gender)
                .email(enrollStudentDto.getEmail())
                .advisor(advisor)
                .grade(enrollStudentDto.getGrade())
                .build();
        // Entity 저장
        studentRepository.save(student);
    }

    // 학적상태 변경
    @Override
    public void changeStudentStatus(String studentNo, StudentStatus status) {
        // 학번으로 학생 정보 조회
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생정보 없음: " + studentNo));
        // 학적상태 코드 조회
        StudentStatusInfo statusInfo = studentStatusInfoRepository.findById(status.getCode())
                .orElseThrow(() -> new EntityNotFoundException("학적상태코드 없음: " + status.getCode()));
        // 학적 상태 변경
        student.changeStatus(statusInfo);
    }

    // 학생 단건 조회
    @Override
    public StudentDto getStudent(String studentNo) {
        // 학생 번호로 학생 정보 조회
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 정보 없음: " + studentNo));
        // Student 엔티티를 StudentDto로 변환하여 반환
        return toStudentDto(student);
    }

    // 학생 리스트 조회
    @Override
    public List<StudentDto> getStudentList(StudentSearchDto studentSearchDto) {
        // 검색 조건에 맞는 학생 리스트 조회
        List<Student> students = studentRepository.search(studentSearchDto);
        // 각 학생 엔티티를 StudentDto로 변환하여 반환
        return students.stream().map(this::toStudentDto).collect(Collectors.toList());
    }

    // 관리자 학생정보 수정
    @Override
    public void adminUpdateStudentInfo(String studentNo, AdminUpdateStudentDto adminUpdateStudentDto) {
        // 학번 으로 학생 정보 조회
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생정보 없음: " + studentNo));
        // 학과 코드 조회
        SchoolSubject schoolSubject = schoolSubjectRepository.findById(adminUpdateStudentDto.getSchoolSubjectCode())
                .orElseThrow(() -> new EntityNotFoundException("학과 코드 없음: " + adminUpdateStudentDto.getSchoolSubjectCode()));
        // 성별 코드 조회
        Employee advisor = employeeRepository.findById(adminUpdateStudentDto.getAdvisorNo())
                .orElseThrow(() -> new EntityNotFoundException("담당 교수 정보 없음: " + adminUpdateStudentDto.getAdvisorNo()));
        // 지도교수 정보 조회
        StudentStatusInfo statusInfo = studentStatusInfoRepository.findById(adminUpdateStudentDto.getStudentStatusCode())
                .orElseThrow(() -> new EntityNotFoundException("학적 상태코드 없음: " + adminUpdateStudentDto.getStudentStatusCode()));
        // 계좌번호 조회
        BankAccount bankAccount = null;
        String bankAccountNo = adminUpdateStudentDto.getBankAccountNo();
        if (bankAccountNo != null && !bankAccountNo.isEmpty()) {
            bankAccount = bankAccountRepository.findById(bankAccountNo)
                    .orElseThrow(() -> new EntityNotFoundException("계좌번호 없음 : " + bankAccountNo));
        }
        // 학생 정보 업데이트
        student.adminUpdate(adminUpdateStudentDto, schoolSubject, null, advisor, bankAccount, statusInfo, null);
    }

    // 내 정보 수정
    @Override
    public void updateMyInfo(String studentNo, UpdateStudentDto dto) {
        // 학번으로 학생 정보 조회
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 정보가 없습니다: " + studentNo));
        // 계좌번호 조회
        BankAccount account = null;
        if (dto.getBankAccountNo() != null) {
            account = bankAccountRepository.findById(dto.getBankAccountNo())
                    .orElseThrow(() -> new EntityNotFoundException("계좌정보 없음"));
        }
        // 학생 정보 업데이트
        student.updateMyInfo(dto, account);
    }

    // 내 비밀번호 변경
    @Override
    public void changeMyPassword(String studentNo, ChangePasswordDto dto) {
        // 학번으로 학생 정보 조회
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 정보가 없습니다: " + studentNo));
        // 로그인 정보 조회
        LoginInfo loginInfo = student.getLoginInfo();
        // 기존 비밀번호 검증
        if (!passwordEncoder.matches(dto.getCurrentPassword(), loginInfo.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        // 새 비밀번호 암호화
        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());
        // 암호화된 비밀번호로 변경
        loginInfo.changePassword(encodedNewPassword);
    }

    // 학번 생성 로직
    private String generateStudentNo(LocalDate admissionDate, String schoolSubjectCode) {
        // 입학 연도를 기준으로 학번 생성
        String admissionYear = String.valueOf(admissionDate.getYear());
        // 학과 코드가 3자리로 가정
        Optional<String> lastStudentNo = studentRepository.findTopByStudentNoStartingWithAndStudentNoContainingOrderByStudentNoDesc(
                admissionYear, schoolSubjectCode);
        // 시퀀스 번호 생성
        int sequence = 1;
        if (lastStudentNo.isPresent()) {
            String lastSeqStr = lastStudentNo.get().substring(lastStudentNo.get().length() - 3);
            sequence = Integer.parseInt(lastSeqStr) + 1;
        }
        // 학번 형식: YYYY + 학과 코드 + 3자리 시퀀스
        return String.format("%s%s%03d", admissionYear, schoolSubjectCode, sequence);
    }

    // 로그인 정보 생성 및 저장
    private LoginInfo createAndSaveLoginInfo(String loginId, LocalDate birthDate) {
        // 생년월일을 기반으로 비밀번호 생성
        String rawPassword = birthDate.toString().replace("-", "");
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // 로그인 정보 생성
        LoginInfo loginInfo = LoginInfo.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .userType("S")
                .build();
        // 로그인 정보 저장
        return loginInfoRepository.save(loginInfo);
    }

    // 학생 페이지 조회
    public Page<StudentDto> getStudentPage(StudentSearchDto cond, Pageable pageable) {
        // 검색 조건에 맞는 학생 페이지 조회
        Page<Student> page = studentRepository.searchPage(cond, pageable);
        // 페이지의 각 학생 엔티티를 StudentDto로 변환하여 반환
        return page.map(this::toStudentDto);
    }

    // Student 엔티티를 StudentDto로 변환
    private StudentDto toStudentDto(Student student) {
        if (student == null) return null;
        return StudentDto.builder()
                .studentNo(student.getStudentNo())
                .name(student.getName())
                .email(student.getEmail())
                .clubCode(student.getClubCode())
                .grade(student.getGrade())
                .advisorName(student.getAdvisor() != null ? student.getAdvisor().getName() : null)
                .studentStatusName(student.getStudentStatus() != null ? student.getStudentStatus().getStatusName() : null)
                .schoolSubjectName(student.getSchoolSubject() != null ? student.getSchoolSubject().getSubjectName() : null)
                .build();
    }
}