package com.secondprojinitiumback.user.student.domain;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.domain.University;
import com.secondprojinitiumback.common.domain.base.BaseEntity;
import com.secondprojinitiumback.common.login.domain.LoginInfo;
import com.secondprojinitiumback.user.student.dto.AdminUpdateStudentDto;
import com.secondprojinitiumback.user.student.dto.UpdateStudentDto;
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
public class Student extends BaseEntity {
    // 학생 번호 (학번)
    @Id
    @Column(name = "STDNT_NO", length = 10)
    private String studentNo;
    // 로그인 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", nullable = false, unique = true)
    private LoginInfo loginInfo;
    // 학교 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIV_CD", referencedColumnName = "UNIV_CD", nullable = false, unique = true)
    private University school;
    // 학과 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCSBJT_NO", nullable = false)
    private SchoolSubject schoolSubject;
    // 동아리 코드
    @Column(name = "CLUB_NO", length = 5)
    private String clubCode;
    // 학적 상태 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_STTS_CD")
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
    @PastOrPresent(message = "입학일자는 오늘 이전이어야 합니다.")
    private LocalDate admissionDate;
    // 생년월일
    @Column(name = "STDNT_BRDT", nullable = false)
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birthDate;
    // 성별 코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CD", referencedColumnName = "id.code"),
            @JoinColumn(name = "CD_SE", referencedColumnName = "id.codeGroup")
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
    @Pattern(regexp = "10|20|30|40", message = "학년은 10, 20, 30, 40 중 하나여야 합니다.")
    private String grade;

    // 생성자
    @Builder
    public Student(String studentNo,
                   LoginInfo loginInfo,
                   University school,
                   SchoolSubject schoolSubject,
                   String clubCode,
                   StudentStatusInfo studentStatus,
                   BankAccount bankAccount,
                   String name,
                   LocalDate admissionDate,
                   LocalDate birthDate,
                   CommonCode gender,
                   String email,
                   Employee advisor,
                   String grade) {
        this.studentNo = studentNo;
        this.loginInfo = loginInfo;
        this.school = school;
        this.schoolSubject = schoolSubject;
        this.clubCode = clubCode;
        this.studentStatus = studentStatus;
        this.bankAccount = bankAccount;
        this.name = name;
        this.admissionDate = admissionDate;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.advisor = advisor;
        this.grade = grade;
    }

    // 학적상태 변경
    public void changeStatus(StudentStatusInfo newStatus) {
        this.studentStatus = newStatus;
    }

    public void updateMyInfo(UpdateStudentDto dto, BankAccount bankAccount) {
        if (dto.getEmail() != null) {
            this.email = dto.getEmail();
        }
        this.bankAccount = bankAccount;
    }

    // 관리자 정보 수정
    public void adminUpdate(AdminUpdateStudentDto dto,
                            SchoolSubject schoolSubject,
                            CommonCode gender,
                            Employee advisor,
                            BankAccount bankAccount,
                            StudentStatusInfo studentStatus,
                            University school) {
        if (dto.getEmail() != null) this.email = dto.getEmail();
        if (dto.getClubCode() != null) this.clubCode = dto.getClubCode();
        if (dto.getGrade() != null) this.grade = dto.getGrade();
        if (schoolSubject != null) this.schoolSubject = schoolSubject;
        if (gender != null) this.gender = gender;
        if (advisor != null) this.advisor = advisor;
        if (bankAccount != null) this.bankAccount = bankAccount;
        if (studentStatus != null) this.studentStatus = studentStatus;
        if (school != null) this.school = school;
    }

}
