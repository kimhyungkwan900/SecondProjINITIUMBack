package com.secondprojinitiumback.common.login.repository;

import com.secondprojinitiumback.common.login.domain.LoginAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAuthInfoRepository extends JpaRepository<LoginAuthInfo, Long> {
}
