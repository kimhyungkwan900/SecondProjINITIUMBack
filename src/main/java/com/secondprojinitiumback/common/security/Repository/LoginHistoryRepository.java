package com.secondprojinitiumback.common.security.Repository;

import com.secondprojinitiumback.common.security.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
