package com.secondprojinitiumback.admin.extracurricular.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "emp_info")
@Getter
@Setter
public class EmpInfo {
    @Id
    @Column(name = "EMP_NO", nullable = false)
    private String empNo;
}