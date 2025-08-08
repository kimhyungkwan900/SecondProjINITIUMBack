package com.secondprojinitiumback.common.security.Repository;

import com.secondprojinitiumback.common.security.domain.LoginAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginAuthInfoRepository extends JpaRepository<LoginAuthInfo, Long> {
    Optional<LoginAuthInfo> findByAccessToken(String accessToken);
    Optional<LoginAuthInfo> findByRefreshToken(String refreshToken);
}
