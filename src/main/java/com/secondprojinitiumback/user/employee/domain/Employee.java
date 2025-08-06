package com.secondprojinitiumback.user.employee.domain;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.domain.base.BaseEntity;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.user.employee.dto.AdminUpdateEmployeeDto;
import com.secondprojinitiumback.user.employee.dto.EmployeeUpdateMyInfoDto;
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

    // 교직원 번호 (사번)
    @Id
    @Column(name = "EMP_NO", length = 10)
    private String empNo;

    // 로그인 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", nullable = false, unique = true)
    private LoginInfo loginInfo;

    // 소속 학교 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCSBJT_NO", nullable = false)
    private SchoolSubject schoolSubject;

    // 상태 코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "EMP_STTS_CD", referencedColumnName = "EMP_STTS_CD"),
            @JoinColumn(name = "EMP_STTS_CD_SE", referencedColumnName = "EMP_STTS_CD_SE")
    })
    private EmployeeStatusInfo employeeStatus;

    // 은행 계좌 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTNO")
    private BankAccount bankAccount;

    // 이름
    @Column(name = "EMP_NM", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "GNDR_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "GNDR_CD_SE", referencedColumnName = "CD_SE")
    })
    private CommonCode gender;

    @Column(name = "EMP_BRDT", nullable = false)
    @PastOrPresent(message = "생년월일은 과거나 오늘이어야 합니다.")
    private LocalDate birthDate;

    @Column(name = "EMP_EMAIL", length = 320, unique = true)
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(name = "EMP_TEL", length = 11, unique = true)
    @Pattern(regexp = "\\d{10,11}", message = "전화번호는 10~11자리 숫자여야 합니다.")
    private String tel;

    private Employee(String empNo,
                     LoginInfo loginInfo,
                     SchoolSubject schoolSubject,
                     BankAccount bankAccount,
                     String name,
                     CommonCode gender,
                     LocalDate birthDate,
                     String email,
                     String tel,
                     EmployeeStatusInfo employeeStatus) {
        this.empNo = empNo;
        this.loginInfo = loginInfo;
        this.schoolSubject = schoolSubject;
        this.bankAccount = bankAccount;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.tel = tel;
        this.employeeStatus = employeeStatus;
    }

    public static Employee create(String empNo,
                                  LoginInfo loginInfo,
                                  SchoolSubject schoolSubject,
                                  BankAccount bankAccount,
                                  String name,
                                  CommonCode gender,
                                  LocalDate birthDate,
                                  String email,
                                  String tel,
                                  EmployeeStatusInfo employeeStatus) {
        return new Employee(empNo, loginInfo, schoolSubject, bankAccount, name, gender, birthDate, email, tel, employeeStatus);
    }

    public void adminUpdate(AdminUpdateEmployeeDto dto, SchoolSubject schoolSubject, CommonCode gender, EmployeeStatusInfo employeeStatus) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getBirthDate() != null) this.birthDate = dto.getBirthDate();
        if (gender != null) this.gender = gender;
        if (dto.getEmail() != null) this.email = dto.getEmail();
        if (dto.getTel() != null) this.tel = dto.getTel();
        if (schoolSubject != null) this.schoolSubject = schoolSubject;
        if (employeeStatus != null) this.employeeStatus = employeeStatus;
    }

    public void updateMyInfo(EmployeeUpdateMyInfoDto dto, BankAccount bankAccount) {
        if (dto.getEmail() != null) this.email = dto.getEmail();
        if (dto.getTel() != null) this.tel = dto.getTel();
        if (dto.getBirthDate() != null) this.birthDate = dto.getBirthDate();
        if (bankAccount != null) this.bankAccount = bankAccount;
    }

    public void changeStatus(EmployeeStatusInfo newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("변경할 상태 정보가 없습니다.");
        }
        this.employeeStatus = newStatus;
    }
}
