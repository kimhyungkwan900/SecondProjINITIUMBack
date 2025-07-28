package com.secondprojinitiumback.admin.extracurricular.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emp_info")
public class EmpInfo {
    @Id
    @Column(name = "EMP_NO", nullable = false)
    private String empNo;
}
