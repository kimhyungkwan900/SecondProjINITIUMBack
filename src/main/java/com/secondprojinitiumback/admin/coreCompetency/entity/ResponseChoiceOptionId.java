package com.secondprojinitiumback.admin.coreCompetency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseChoiceOptionId implements Serializable {
    private Long id; // 보기 ID
    private Long questionId; // 문항 ID
}

