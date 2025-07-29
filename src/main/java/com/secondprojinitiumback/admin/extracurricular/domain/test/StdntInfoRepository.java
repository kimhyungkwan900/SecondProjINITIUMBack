package com.secondprojinitiumback.admin.extracurricular.domain.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StdntInfoRepository extends JpaRepository<StdntInfo, String> {
}
