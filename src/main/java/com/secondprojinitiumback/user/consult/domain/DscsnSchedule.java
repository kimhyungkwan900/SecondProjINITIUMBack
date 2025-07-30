package com.secondprojinitiumback.user.consult.domain;

import com.secondprojinitiumback.user.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name="DSCSN_DT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DscsnSchedule {

    @Id
    @Column(name = "DSCSN_DT_ID", nullable = false, length = 5)
    private String dscsnDtId; //상담일정 ID

    @Column(name = "DSCSN_PSBLTY_D", nullable = false, length = 8)
    private String possibleDate; //상담 가능 날짜

    @Column(name = "DSCSN_PSBLTY_T", nullable = false, length = 4)
    private String possibleTime; //상담 가능 시간

    @Column(name = "DSCSN_YN", nullable = false, length = 1)
    private String dscsnYn; //예약 여부

    @ManyToOne
    @JoinColumn(name = "EMP_NO", foreignKey = @ForeignKey(name = "FK_EMP_NO"))
    private Employee employee;  //사번

    //예약 여부 변경 메소드
    public void updateDscsnYn() {

        if(this.dscsnYn.equals("Y")) {
            this.dscsnYn = "N";
        } else if(this.dscsnYn.equals("N")) {
            this.dscsnYn = "Y";
        }
    }
}
