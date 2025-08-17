package com.secondprojinitiumback.common.security.Repository;

import com.secondprojinitiumback.common.security.domain.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, String> {
    Optional<EmailAuth> findByEmail(String email);
    void deleteByEmail(String email);
}
