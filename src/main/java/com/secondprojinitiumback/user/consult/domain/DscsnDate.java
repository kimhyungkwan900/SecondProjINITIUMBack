package com.secondprojinitiumback.user.consult.domain;

import com.secondprojinitiumback.user.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="DSCSN_DT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DscsnDate {

    @Id
    @Column(name = "DSCSN_DT_ID", nullable = false, length = 5)
    private String dscsnDtId; //상담일정 ID

    @Column(name = "DSCSN_PSBLTY_D", nullable = false, length = 8)
    private String possibleDate; //상담 가능 날짜

    @Column(name = "DSCSN_PSBLTY_T", nullable = false, length = 4)
    private String possibleTime; //상담 가능 시간

    @ManyToOne
    @JoinColumn(name = "EMP_NO", foreignKey = @ForeignKey(name = "FK_EMP_NO"))
    private Employee employee;  //사번
}
