package com.secondprojinitiumback.common.security.Repository;

import com.secondprojinitiumback.common.security.domain.LoginAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAuthInfoRepository extends JpaRepository<LoginAuthInfo, Long> {
}
