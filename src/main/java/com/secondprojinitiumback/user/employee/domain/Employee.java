package com.secondprojinitiumback.user.employee.domain;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.login.domain.LoginInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "EMP_INFO",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "EMP_EMAIL"),
                @UniqueConstraint(columnNames = "EMP_TEL")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @Column(name = "EMP_NO", length = 10)
    private String empNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", nullable = false, unique = true)
    private LoginInfo loginInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCSBJT_NO", nullable = false)
    private SchoolSubject schoolSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTNO")
    private BankAccount bankAccount;

    @Column(name = "EMP_NM", length = 100, nullable = false)
    private String name;

    @Column(name = "GNDR_NM", length = 300, nullable = false)
    @Pattern(regexp = "Male|Female|Unknown", message = "성별은 Male, Female, Unknown 중 하나여야 합니다.")
    private String gender;

    @Column(name = "EMP_BRDT", nullable = false)
    @PastOrPresent(message = "생년월일은 과거나 오늘이어야 합니다.")
    private LocalDate birthDate;

    @Column(name = "EMP_EMAIL", length = 320, unique = true)
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(name = "EMP_TEL", length = 11, unique = true)
    @Pattern(regexp = "\\d{10,11}", message = "전화번호는 10~11자리 숫자여야 합니다.")
    private String tel;


    public static Employee create(String empNo,
                                  LoginInfo loginInfo,
                                  SchoolSubject schoolSubject,
                                  BankAccount bankAccount,
                                  String name,
                                  String gender,
                                  LocalDate birthDate,
                                  String email,
                                  String tel) {
        return new Employee(empNo, loginInfo, schoolSubject, bankAccount, name, gender, birthDate, email, tel);
    }

    private Employee(String empNo,
                     LoginInfo loginInfo,
                     SchoolSubject schoolSubject,
                     BankAccount bankAccount,
                     String name,
                     String gender,
                     LocalDate birthDate,
                     String email,
                     String tel) {
        this.empNo = empNo;
        this.loginInfo = loginInfo;
        this.schoolSubject = schoolSubject;
        this.bankAccount = bankAccount;
        this.name = name;
        this.gender = gender == null ? "Unknown" : gender;
        this.birthDate = birthDate;
        this.email = email;
        this.tel = tel;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changeTel(String newTel) {
        this.tel = newTel;
    }
}