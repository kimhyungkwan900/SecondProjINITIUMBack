package com.secondprojinitiumback.common.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    // 현재 사용자의 정보를 제공하는 AuditorAware 구현체
    @Override
    public Optional<String> getCurrentAuditor() {
        // Spring Security 또는 세션 기반 사용자 정보 활용
        return Optional.of("system"); // 예: SecurityContextHolder.getContext().getAuthentication().getName()
    }
}

/* 로그인 구현 이후 변경
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        // Case 1: principal이 username(String)일 경우
        if (principal instanceof String) {
            return Optional.of((String) principal);
        }

        // Case 2: principal이 UserDetails 또는 사용자 정의 객체일 경우
        if (principal instanceof UserDetails userDetails) {
            return Optional.of(userDetails.getUsername());
        }

        return Optional.empty();
    }
}
*/
