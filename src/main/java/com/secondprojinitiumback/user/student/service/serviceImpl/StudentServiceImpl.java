package com.secondprojinitiumback.user.student.service.serviceImpl;

import com.secondprojinitiumback.common.bank.Repository.BankAccountRepository;
import com.secondprojinitiumback.common.bank.domain.BankAccount;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.domain.University;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.common.security.dto.CreateLoginDto;
import com.secondprojinitiumback.common.security.service.LoginInfoService;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.common.repository.UniversityRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
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
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final CommonCodeRepository commonCodeRepository;
    private final EmployeeRepository employeeRepository;
    private final BankAccountRepository bankAccountRepository;
    private final StudentStatusInfoRepository studentStatusInfoRepository;
    private final LoginInfoService loginInfoService;
    private final UniversityRepository universityRepository;

    // 학생 입학 (최초 등록)
    @Override
    public void enrollStudent(EnrollStudentDto dto) {
        SchoolSubject schoolSubject = findSchoolSubjectByCode(dto.getSchoolSubjectCode());
        University university = findUniversityByCode(dto.getUniversityCode());
        String studentNo = generateStudentNo(dto.getAdmissionDate(), dto.getSchoolSubjectCode());

        CreateLoginDto createLoginDto = CreateLoginDto.builder()
                .loginId(studentNo)
                .userType("S")
                .birthDate(dto.getBirthDate())
                .build();
        LoginInfo loginInfo = loginInfoService.createLoginInfo(createLoginDto);

        CommonCode gender = findCommonCode(dto.getGender(), "CO0001");
        Employee advisor = findEmployeeById(dto.getAdvisorNo());
        BankAccount bankAccount = findBankAccountByNoNullable(dto.getBankAccountNumber());
        StudentStatusInfo initialStatus = findStudentStatusByCode(dto.getStudentStatusCode(), "SL0030");

        Student student = Student.create(
                studentNo, loginInfo, university, schoolSubject, dto.getName(),
                dto.getAdmissionDate(), dto.getBirthDate(), gender, dto.getEmail(),
                advisor, dto.getGrade(), bankAccount, dto.getClubCode(), initialStatus
        );

        studentRepository.save(student);
    }

    @Override
    public void changeStudentStatus(String studentNo, String statusCode) {
        Student student = findStudentById(studentNo);
        StudentStatusInfo statusInfo = findStudentStatusByCode(statusCode, "SL0030");
        student.changeStatus(statusInfo);
    }

    @Override
    public StudentDto getStudent(String studentNo) {
        Student student = findStudentById(studentNo);
        return toStudentDto(student);
    }

    @Override
    public List<StudentDto> getStudentList(StudentSearchDto searchDto) {
        List<Student> students = studentRepository.search(searchDto);
        return students.stream().map(this::toStudentDto).collect(Collectors.toList());
    }

    @Override
    public void adminUpdateStudentInfo(String studentNo, AdminUpdateStudentDto dto) {
        Student student = findStudentById(studentNo);
        SchoolSubject schoolSubject = findSchoolSubjectByCode(dto.getSchoolSubjectCode());
        Employee advisor = findEmployeeById(dto.getAdvisorNo());
        StudentStatusInfo statusInfo = findStudentStatusByCode(dto.getStudentStatusCode(), "SL0030");
        BankAccount bankAccount = findBankAccountByNoNullable(dto.getBankAccountNo());
        CommonCode gender = findCommonCodeNullable(dto.getGender(), "CO0001");
        University university = findUniversityByCodeNullable(dto.getUniversityCode());

        student.adminUpdate(dto, schoolSubject, gender, advisor, bankAccount, statusInfo, university);
    }

    @Override
    public void updateMyInfo(String studentNo, UpdateStudentDto dto) {
        Student student = findStudentById(studentNo);
        BankAccount bankAccount = findBankAccountByNoNullable(dto.getBankAccountNo());
        student.updateMyInfo(dto, bankAccount);
    }

    // 학생 페이지 조회
    public Page<StudentDto> getStudentPage(StudentSearchDto cond, Pageable pageable) {
        // 검색 조건에 맞는 학생 페이지 조회
        Page<Student> page = studentRepository.searchPage(cond, pageable);
        // 페이지의 각 학생 엔티티를 StudentDto로 변환하여 반환
        return page.map(this::toStudentDto);
    }

    // entity 조회 메서드들

    private Student findStudentById(String studentNo) {
        return studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 정보 없음: " + studentNo));
    }

    private SchoolSubject findSchoolSubjectByCode(String code) {
        return schoolSubjectRepository.findBySubjectCode(code)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 학과 코드: " + code));
    }

    private University findUniversityByCode(String code) {
        return universityRepository.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 대학 코드: " + code));
    }

    private University findUniversityByCodeNullable(String code) {
        if (code == null || code.isBlank()) return null;
        return findUniversityByCode(code);
    }

    private Employee findEmployeeById(String employeeNo) {
        return employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 교직원 번호: " + employeeNo));
    }

    private CommonCode findCommonCode(String code, String groupCode) {
        return commonCodeRepository.findById_CodeAndId_CodeGroup(code, groupCode)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 공통 코드: " + code));
    }

    private CommonCode findCommonCodeNullable(String code, String groupCode) {
        if (code == null || code.isBlank()) return null;
        return findCommonCode(code, groupCode);
    }

    private BankAccount findBankAccountByNoNullable(String accountNo) {
        if (accountNo == null || accountNo.isBlank()) return null;
        return bankAccountRepository.findByAccountNumber(accountNo)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 계좌번호: " + accountNo));
    }

    private StudentStatusInfo findStudentStatusByCode(String code, String groupCode) {
        return studentStatusInfoRepository.findByStudentStatusCodeAndStudentStatusCodeSe(code, groupCode)
                .orElseThrow(() -> new EntityNotFoundException("학적 상태 코드 없음: " + code));
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
                .studentStatusName(student.getStudentStatus() != null ? student.getStudentStatus().getStudentStatusName() : null)
                .schoolSubjectName(student.getSchoolSubject() != null ? student.getSchoolSubject().getSubjectName() : null)
                .build();
    }
}