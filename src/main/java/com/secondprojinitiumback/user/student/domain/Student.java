package com.secondprojinitiumback.user.student.domain;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.login.domain.LoginInfo; // [추가] LoginInfo 임포트
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
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

    @Id
    @Column(name = "STDNT_NO", length = 10)
    private String studentNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", nullable = false, unique = true)
    private LoginInfo loginInfo;

    // 학과 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCSBJT_NO", nullable = false)
    private SchoolSubject schoolSubject;

    @Column(name = "CLUB_NO", length = 5)
    private String clubCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_STTS_CD")
    private StudentStatusInfo studentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTNO")
    private BankAccount bankAccount;

    @Column(name = "STDNT_NM", length = 100, nullable = false)
    private String name;

    @Column(name = "MTCLTN_YMD")
    @PastOrPresent(message = "입학일자는 오늘 이전이어야 합니다.")
    private LocalDate admissionDate;

    @Column(name = "STDNT_BRDT", nullable = false)
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birthDate;

    @Column(name = "GNDR_NM", length = 300)
    @Pattern(regexp = "Male|Female|Unknown", message = "성별은 Male, Female, Unknown 중 하나여야 합니다.")
    private String gender;

    @Column(name = "STDNT_EMAIL", length = 320, unique = true)
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(name = "ACAVSR_NM", length = 100, nullable = false)
    private String advisorName;

    @Column(name = "GRADE", length = 5, nullable = false)
    @Pattern(regexp = "10|20|30|40", message = "학년은 10, 20, 30, 40 중 하나여야 합니다.")
    private String grade;

    @Builder
    public Student(String studentNo,
                   LoginInfo loginInfo,
                   SchoolSubject schoolSubject,
                   String clubCode,
                   StudentStatusInfo studentStatus,
                   BankAccount bankAccount,
                   String name,
                   LocalDate admissionDate,
                   LocalDate birthDate,
                   String gender,
                   String email,
                   String advisorName,
                   String grade) {
        this.studentNo = studentNo;
        this.loginInfo = loginInfo;
        this.schoolSubject = schoolSubject;
        this.clubCode = clubCode;
        this.studentStatus = studentStatus;
        this.bankAccount = bankAccount;
        this.name = name;
        this.admissionDate = admissionDate;
        this.birthDate = birthDate;
        this.gender = gender == null ? "Unknown" : gender;
        this.email = email;
        this.advisorName = advisorName;
        this.grade = grade;
    }

}
