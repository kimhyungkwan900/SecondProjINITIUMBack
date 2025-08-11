package com.secondprojinitiumback.user.student.service.serviceImpl;

import com.secondprojinitiumback.common.bank.Repository.BankAccountRepository;
import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.converter.LocalDateToChar8Converter;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.CommonCodeId;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.domain.University;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
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

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

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
    private final LocalDateToChar8Converter dateConverter;

    @Override
    public StudentDto enrollStudent(EnrollStudentDto dto) {
        // 필수 정보 검증
        SchoolSubject schoolSubject = findSchoolSubjectByCode(dto.getSchoolSubjectCode());
        University university = findUniversityByCode(dto.getUniversityCode());
        String studentNo = generateStudentNo(dto.getAdmissionDate(), dto.getSchoolSubjectCode());

        // 로그인 정보 생성
        CreateLoginDto createLoginDto = CreateLoginDto.builder()
                .loginId(studentNo)
                .userType("S")
                .birthDate(dto.getBirthDate())
                .build();
        LoginInfo loginInfo = loginInfoService.createLoginInfo(createLoginDto);

        // 공통 코드, 교직원, 계좌 정보 조회
        CommonCode gender = findCommonCode(dto.getGender(), "CO0001");
        Employee advisor = findEmployeeById(dto.getAdvisorNo());
        BankAccount bankAccount = findBankAccountByNoNullable(dto.getBankAccountNo());
        StudentStatusInfo initialStatus = findStudentStatusByCode(dto.getStudentStatusCode());

        // 학생 엔티티 생성 및 저장
        Student student = Student.create(
                studentNo, loginInfo, university, schoolSubject, dto.getName(),
                dto.getAdmissionDate(), dto.getBirthDate(), gender, dto.getEmail(),
                advisor, dto.getGrade(), bankAccount, dto.getClubCode(), initialStatus
        );

        // 학생 정보 저장
        Student savedStudent = studentRepository.save(student);
        // DTO 변환 후 반환
        return toStudentDto(savedStudent);
    }

    @Override
    public StudentDto changeStudentStatus(String studentNo, String statusCode) {
        // 학생과 상태 정보 조회
        Student student = findStudentById(studentNo);
        StudentStatusInfo statusInfo = findStudentStatusByCode(statusCode);
        // 상태 변경
        student.changeStatus(statusInfo);
        // 학생 정보 저장
        return toStudentDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudent(String studentNo) {
        // 학생 정보 조회
        Student student = findStudentById(studentNo);
        // DTO 변환 후 반환
        return toStudentDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> getStudentPage(StudentSearchDto cond, Pageable pageable) {
        // 검색 조건에 맞는 학생 정보 페이지 조회
        Page<Student> page = studentRepository.searchPage(cond, pageable);
        // DTO 변환 후 반환
        return page.map(this::toStudentDto);
    }

    @Override
    @Transactional
    public StudentDto adminUpdateStudentInfo(String studentNo, AdminUpdateStudentDto dto) {
        // 학생, 학과, 교직원, 학적 상태, 계좌 정보 조회
        Student student = findStudentById(studentNo);
        SchoolSubject schoolSubject = findSchoolSubjectByCodeNullable(dto.getSchoolSubjectCode());
        Employee advisor = findEmployeeByIdNullable(dto.getAdvisorNo());
        StudentStatusInfo statusInfo = findStudentStatusByCodeNullable(dto.getStudentStatusCode());
        BankAccount bankAccount = findBankAccountByNoNullable(dto.getBankAccountNo());
        // 공통 코드 조회 (성별)
        CommonCode gender = findCommonCodeNullable(dto.getGender(), "CO0001");
        // 대학 정보 조회 (nullable 처리)
        University university = findUniversityByCodeNullable(dto.getUniversityCode());
        
        // 날짜 변환 (String -> LocalDate)
        LocalDate birthDate = parseLocalDateNullable(dto.getBirthDate());
        LocalDate admissionDate = parseLocalDateNullable(dto.getAdmissionDate());

        // 학생 정보 업데이트
        student.adminUpdate(dto, schoolSubject, gender, advisor, bankAccount, statusInfo, university, birthDate, admissionDate);
        // 학생 정보 저장
        return toStudentDto(student);
    }

    @Override
    @Transactional
    public StudentDto updateMyInfo(String studentNo, UpdateStudentDto dto) {
        Student student = findStudentById(studentNo);

        // 이메일 변경
        if (dto.getEmail() != null) {
            String newEmail = dto.getEmail().trim();
            if (!newEmail.isEmpty() && !newEmail.equals(student.getEmail())) {
                student.changeEmail(newEmail);
            }
        }

        // 계좌 변경
        if (dto.getBankAccountNo() != null && !dto.getBankAccountNo().isBlank()) {
            BankAccount account = upsertStudentBankAccount(studentNo, dto);
            student.changeBankAccount(account);
        }



        return toStudentDto(student);
    }

    private BankAccount upsertStudentBankAccount(String studentNo, UpdateStudentDto dto) {
        final String actNo = dto.getBankAccountNo().trim();

        return bankAccountRepository.findById(actNo)
                .map(acc -> {

                    // 소유자가 같지않다면 같도록 변경
                    if (!studentNo.equals(acc.getOwnerId())) {
                        acc.changeOwner(studentNo);
                    }
                    // 사용여부 Y
                    if (!"Y".equalsIgnoreCase(acc.getUseYn())) {
                        acc.changeUseYn("Y");
                    }
                    // 계좌유형 입금
                    if (!"DPST".equalsIgnoreCase(acc.getAccountType())) {
                        acc.changeAccountType("DPST");
                    }
                    // 은행코드 적용
                    applyBankCodeIfPresent(acc, dto);
                    return acc;
                })
                .orElseGet(() -> {
                    // 신규 생성
                    return bankAccountRepository.save(
                            BankAccount.builder()
                                    .accountNo(actNo)
                                    .ownerId(studentNo)
                                    .accountType("DPST")
                                    .useYn("Y")
                                    .bankCode(resolveBankCodeNullable(dto))
                                    .build()
                    );
                });
    }

    // entity 조회 메서드들

    // 학번으로 학생 정보 조회
    private Student findStudentById(String studentNo) {
        return studentRepository.findById(studentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));
    }

    // 학과 코드로 학과 정보 조회
    private SchoolSubject findSchoolSubjectByCode(String code) {
        return schoolSubjectRepository.findBySubjectCode(code)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
    }
    
    // nullable 처리된 학과 코드로 학과 정보 조회
    private SchoolSubject findSchoolSubjectByCodeNullable(String code) {
        if (code == null || code.isBlank()) return null;
        return findSchoolSubjectByCode(code);
    }

    // 대학 코드로 대학 정보 조회
    private University findUniversityByCode(String code) {
        return universityRepository.findById(code)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
    }

    // nullable 처리된 대학 코드로 대학 정보 조회
    private University findUniversityByCodeNullable(String code) {
        if (code == null || code.isBlank()) return null;
        return findUniversityByCode(code);
    }

    // 교직원 번호로 교직원 정보 조회
    private Employee findEmployeeById(String employeeNo) {
        return employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new CustomException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }
    
    // nullable 처리된 교직원 번호로 교직원 정보 조회
    private Employee findEmployeeByIdNullable(String employeeNo) {
        if (employeeNo == null || employeeNo.isBlank()) return null;
        return findEmployeeById(employeeNo);
    }

    // 공통 코드 조회 (코드와 그룹 코드로)
    private CommonCode findCommonCode(String code, String groupCode) {
        return commonCodeRepository.findById_CodeAndId_CodeGroup(code, groupCode)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
    }

    // nullable 처리된 공통 코드 조회 (코드와 그룹 코드로)
    private CommonCode findCommonCodeNullable(String code, String groupCode) {
        if (code == null || code.isBlank()) return null;
        return findCommonCode(code, groupCode);
    }

    // 계좌 번호로 은행 계좌 정보 조회
    private BankAccount findBankAccountByNoNullable(String accountNo) {
        if (accountNo == null || accountNo.isBlank()) return null;
        return bankAccountRepository.findByAccountNo(accountNo)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
    }

    // 학적 상태 코드로 학적 상태 정보 조회
    private StudentStatusInfo findStudentStatusByCode(String code) {
        return studentStatusInfoRepository.findByIdStudentStatusCodeAndIdStudentStatusCodeSe(code, "SL0030")
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
    }
    
    // nullable 처리된 학적 상태 코드로 학적 상태 정보 조회
    private StudentStatusInfo findStudentStatusByCodeNullable(String code) {
        if (code == null || code.isBlank()) return null;
        return findStudentStatusByCode(code);
    }

    // 은행 코드가 있다면 계좌에 적용
    private void applyBankCodeIfPresent(BankAccount acc, UpdateStudentDto dto) {
        CommonCode code = resolveBankCodeNullable(dto);
        if (code != null) {
            acc.changeBankCode(code);
        }
    }

    // nullable 처리된 은행 코드 조회 (은행 코드와 은행 코드 그룹)
    private CommonCode resolveBankCodeNullable(UpdateStudentDto dto) {
        if (dto.getBankCd() == null) return null;
        return commonCodeRepository.findById(new CommonCodeId("CO0005", dto.getBankCd()))
                .orElseThrow(() -> new CustomException(ErrorCode.COMMON_CODE_NOT_FOUND));
    }
    
    // 날짜 문자열을 LocalDate로 변환 (nullable 처리)
    private LocalDate parseLocalDateNullable(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            // LocalDateToChar8Converter를 사용하여 문자열을 LocalDate로 변환
            return dateConverter.convertToEntityAttribute(dateStr);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
    }
    // 학번 생성 로직
    private String generateStudentNo(LocalDate admissionDate, String schoolSubjectCode) {
        String admissionYear = String.valueOf(admissionDate.getYear());
        Optional<String> lastStudentNo = studentRepository.findTopByStudentNoStartingWithAndStudentNoContainingOrderByStudentNoDesc(
                admissionYear, schoolSubjectCode);
        int sequence = 1;
        if (lastStudentNo.isPresent()) {
            String lastSeqStr = lastStudentNo.get().substring(lastStudentNo.get().length() - 3);
            sequence = Integer.parseInt(lastSeqStr) + 1;
        }
        return String.format("%s%s%03d", admissionYear, schoolSubjectCode, sequence);
    }

    private StudentDto toStudentDto(Student student) {
        if (student == null) return null;
        return StudentDto.builder()
                .studentNo(student.getStudentNo())
                .name(student.getName())
                .email(student.getEmail())
                .clubCode(student.getClubCode())
                .admissionDate(student.getAdmissionDate())
                .birthDate(student.getBirthDate())
                .grade(student.getGrade())
                .advisorId(student.getAdvisor() != null ? student.getAdvisor().getEmpNo() : null)
                .studentStatusCode(student.getStudentStatus() != null ? student.getStudentStatus().getId().getStudentStatusCode() : null)
                .schoolSubjectCode(student.getSchoolSubject() != null ? student.getSchoolSubject().getSubjectCode() : null)
                .genderCode(student.getGender() != null ? student.getGender().getId().getCode() : null)
                .build();
    }
}
