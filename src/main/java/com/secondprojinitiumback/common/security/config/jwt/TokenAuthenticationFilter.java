package com.secondprojinitiumback.common.security.config.jwt;

import com.secondprojinitiumback.common.security.utils.CookieConstants;
import com.secondprojinitiumback.common.security.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);

        if (StringUtils.hasText(jwt)) {
            if (tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Access Token이 만료된 경우, Refresh Token으로 갱신 시도
                tryRefreshToken(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void tryRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            Optional<Cookie> refreshCookie = CookieUtils.getCookie(request, CookieConstants.REFRESH_TOKEN);
            if (refreshCookie.isPresent()) {
                String refreshToken = refreshCookie.get().getValue();
                if (tokenProvider.validateToken(refreshToken)) {
                    // Refresh Token이 유효한 경우, 새로운 Access Token 발급
                    // 이 부분은 실제로는 서비스 레이어에서 처리해야 하지만, 
                    // 필터에서는 간단히 처리
                    response.setHeader("X-Token-Expired", "true");
                }
            }
        } catch (Exception e) {
            // 토큰 갱신 실패 시 무시하고 계속 진행
        }
    }

    private String resolveToken(HttpServletRequest request) {
        // 1. 쿠키에서 Access Token 확인
        Optional<Cookie> cookie = CookieUtils.getCookie(request, CookieConstants.ACCESS_TOKEN);
        if (cookie.isPresent()) {
            return cookie.get().getValue();
        }

        // 2. 쿠키에 없으면 헤더에서 확인 (기존 로직 유지)
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
}