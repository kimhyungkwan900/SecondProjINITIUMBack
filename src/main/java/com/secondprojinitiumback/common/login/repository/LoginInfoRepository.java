package com.secondprojinitiumback.common.login.repository;

import com.secondprojinitiumback.common.login.domain.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, String> {
    Optional<LoginInfo> findByLoginId(String loginId);
}
