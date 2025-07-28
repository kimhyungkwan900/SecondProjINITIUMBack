package com.secondprojinitiumback.admin.extracurricular.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stunt_info")
public class StuntInfo {
    @Id
    @Column(name = "stdnt_no")
    private String stdntNo;
}
