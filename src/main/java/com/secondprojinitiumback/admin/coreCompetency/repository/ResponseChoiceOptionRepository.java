package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.ResponseChoiceOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ResponseChoiceOptionRepository extends JpaRepository<ResponseChoiceOption, Long> {
    List<ResponseChoiceOption> findByQuestion_Id(Long questionId); // 연관 엔티티 ID 접근 시 _ 사용
}
