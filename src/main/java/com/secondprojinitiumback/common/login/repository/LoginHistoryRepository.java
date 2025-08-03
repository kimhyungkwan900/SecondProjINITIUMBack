package com.secondprojinitiumback.common.login.repository;

import com.secondprojinitiumback.common.login.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
