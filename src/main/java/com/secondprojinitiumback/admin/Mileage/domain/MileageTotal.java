package com.secondprojinitiumback.admin.Mileage.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "MLG_TOT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MileageTotal implements Persistable<String> {
/*
    학번을 PK로 사용하고 FK로 사용하기위해 JoinColumn 으로 받아와 ID로 사용하도록 수정했습니다
*/

    @Id
    @Column(name = "STDNT_NO")
    private String studentNo;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId                         // studentNo 필드를 Student 엔티티의 기본 키에 매핑
    @JoinColumn(name = "STDNT_NO") // 외래 키 컬럼 지정 (STDNT_NO)
    private Student student; // 이게 있어야 학번이 PK이자 FK임을 동시에 표현 가능

    @Column(name = "MLG_TOT_SCORE", nullable = false)
    private Integer totalScore; // 누적 점수


    // 👉 신규 여부 플래그 (INSERT 유도)
    @Builder.Default
    @Transient
    private boolean isNew = true;

    // Persistable 구현
    @Override public String getId() { return this.studentNo; }
    @Override public boolean isNew() { return this.isNew || this.totalScore == null; }

    @PostLoad @PostPersist
    public void markNotNew() { this.isNew = false; }

    public void add(int score) {
        this.totalScore = (this.totalScore == null ? 0 : this.totalScore) + score;
    }
    public void subtract(int score) {
        this.totalScore = (this.totalScore == null ? 0 : this.totalScore) - score;
        if (this.totalScore < 0) this.totalScore = 0;
    }
    @PrePersist
    void prePersist() { if (this.totalScore == null) this.totalScore = 0; }
}

