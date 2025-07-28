package com.secondprojinitiumback.user.student.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
지도교수 상담: A
진로취업 상담: C
심리상담: P
학습상담: L
 */

@Entity
@Getter
@Table(name="DSCSN_APPLY")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dscsn_apply {

    @Id
    @Column(name="DSCSN_APPLY_ID", nullable = false, length = 5)
    private String dscsn_apply_id;


}
