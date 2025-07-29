package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CoreCompetencyCategoryRepository extends JpaRepository<CoreCompetencyCategory, Long> {

}
