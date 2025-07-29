package com.secondprojinitiumback.admin.extracurricular.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stdnt_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StdntInfo {
    @Id
    @Column(name = "stdnt_no")
    private String stdntNo;
}
