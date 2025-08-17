package com.secondprojinitiumback.user.student.domain;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.converter.LocalDateToChar8Converter;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.domain.University;
import com.secondprojinitiumback.common.domain.base.BaseEntity;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.user.student.dto.AdminUpdateStudentDto;
import com.secondprojinitiumback.user.student.dto.UpdateStudentDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import com.secondprojinitiumback.user.employee.domain.Employee;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "STDNT_INFO",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "STDNT_EMAIL")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    // 학생 번호 (학번)
    @Id
    @Column(name = "STDNT_NO", length = 10)
    private String studentNo;

    // 로그인 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", nullable = false, unique = true)
    private LoginInfo loginInfo;

    // 학교 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIV_CD", referencedColumnName = "UNIV_CD", nullable = false)
    private University school;

    // 학과 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCSBJT_NO", nullable = false)
    private SchoolSubject schoolSubject;

    // 학적 상태 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "STDNT_STTS_CD", referencedColumnName = "STDNT_STTS_CD"),
            @JoinColumn(name = "STDNT_STTS_CD_SE", referencedColumnName = "STDNT_STTS_CD_SE")
    })
    private StudentStatusInfo studentStatus;

    // 계좌 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTNO")
    private BankAccount bankAccount;

    // 학생 이름
    @Column(name = "STDNT_NM", length = 100, nullable = false)
    private String name;

    // 입학일자
    @Column(name = "MTCLTN_YMD")
    @Convert(converter = LocalDateToChar8Converter.class)
    @PastOrPresent(message = "입학일자는 오늘 이전이어야 합니다.")
    private LocalDate admissionDate;

    // 생년월일
    @Column(name = "STDNT_BRDT", nullable = false)
    @Convert(converter = LocalDateToChar8Converter.class)
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birthDate;

    // 성별 코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "GNDR_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "GNDR_CD_SE", referencedColumnName = "CD_SE")
    })
    private CommonCode gender;

    // 이메일 주소
    @Column(name = "STDNT_EMAIL", length = 320, unique = true)
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    // 지도교수 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACAVSR_NO", nullable = false)
    private Employee advisor;

    // 학년: 10(1학년), 20(2학년), 30(3학년), 40(4학년)
    @Column(name = "GRADE", length = 5, nullable = false)
    @Pattern(regexp = "1|2|3|4", message = "학년은 1, 2, 3, 4 중 하나여야 합니다.")
    private String grade;

    // 생성자
    @Builder
    private Student(String studentNo,
                    LoginInfo loginInfo,
                    University school,
                    SchoolSubject schoolSubject,
                    String name,
                    LocalDate admissionDate,
                    LocalDate birthDate,
                    CommonCode gender,
                    String email,
                    Employee advisor,
                    String grade,
                    BankAccount bankAccount,
                    StudentStatusInfo studentStatus) {
        this.studentNo = studentNo;
        this.loginInfo = loginInfo;
        this.school = school;
        this.schoolSubject = schoolSubject;
        this.name = name;
        this.admissionDate = admissionDate;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.advisor = advisor;
        this.grade = grade;
        this.bankAccount = bankAccount;
        this.studentStatus = studentStatus;
    }

    public static Student create(String studentNo,
                                 LoginInfo loginInfo,
                                 University school,
                                 SchoolSubject schoolSubject,
                                 String name,
                                 LocalDate admissionDate,
                                 LocalDate birthDate,
                                 CommonCode gender,
                                 String email,
                                 Employee advisor,
                                 String grade,
                                 BankAccount bankAccount,
                                 StudentStatusInfo initialStatus) {
        return Student.builder()
                .studentNo(studentNo)
                .loginInfo(loginInfo)
                .school(school)
                .schoolSubject(schoolSubject)
                .name(name)
                .admissionDate(admissionDate)
                .birthDate(birthDate)
                .gender(gender)
                .email(email)
                .advisor(advisor)
                .grade(grade)
                .bankAccount(bankAccount)
                .studentStatus(initialStatus)
                .build();
    }

    // 학적상태 변경
    public void changeStatus(StudentStatusInfo newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("변경할 학적 상태 정보가 없습니다.");
        }
        this.studentStatus = newStatus;
    }

    public void changeEmail(String email) { this.email = email; }

    public void changeBankAccount(@Nullable BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    // 관리자 정보 수정
    public void adminUpdate(AdminUpdateStudentDto dto,
                            SchoolSubject schoolSubject,
                            CommonCode gender,
                            Employee advisor,
                            BankAccount bankAccount,
                            StudentStatusInfo studentStatus,
                            University school,
                            LocalDate birthDate,
                            LocalDate admissionDate) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getEmail() != null) this.email = dto.getEmail();
        if (birthDate != null) this.birthDate = birthDate;
        if (admissionDate != null) this.admissionDate = admissionDate;
        if (dto.getGrade() != null) this.grade = dto.getGrade();
        if (school != null) this.school = school;
        if (schoolSubject != null) this.schoolSubject = schoolSubject;
        if (advisor != null) this.advisor = advisor;
        if (bankAccount != null) this.bankAccount = bankAccount;
        if (studentStatus != null) this.studentStatus = studentStatus;
        if (gender != null) this.gender = gender;
    }

}
