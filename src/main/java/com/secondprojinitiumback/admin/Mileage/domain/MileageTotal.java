package com.secondprojinitiumback.admin.Mileage.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MLG_TOT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MileageTotal {

    @Id
    @JoinColumn(name = "STDNT_NO")
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // 이게 있어야 학번이 PK이자 FK임을 동시에 표현 가능
    private Student student;

    @Column(name = "MLG_TOT_SCORE", nullable = false)
    private Double totalScore; // 누적 점수

    // 누적 점수 더하기
    public void add(int score) {
        this.totalScore += score;
    }

    // 누적 점수 빼기
    public void subtract(int score) {
        this.totalScore -= score;
        if (this.totalScore < 0) {
            this.totalScore = 0.0;
        }
    }
}
