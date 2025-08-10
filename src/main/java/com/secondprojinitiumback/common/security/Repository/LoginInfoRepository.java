package com.secondprojinitiumback.common.security.Repository;

import com.secondprojinitiumback.common.security.domain.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, String> {
    Optional<LoginInfo> findByLoginId(String loginId);
}
