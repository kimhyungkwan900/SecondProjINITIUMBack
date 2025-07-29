package com.secondprojinitiumback.common.audit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditInterceptor implements HandlerInterceptor {

     // 클라이언트의 IP 주소와 프로그램 ID를 요청 헤더에서 추출하여 AuditContextHolder에 저장
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AuditContextHolder.setClientIp(request.getRemoteAddr());

        // 요청 헤더에서 "X-Program-Id"를 추출하여 프로그램 ID로 설정
        String header = request.getHeader("X-Program-Id");

        // 헤더가 null이 아니고 비어있지 않은 경우에만 프로그램 ID를 설정
        if (header != null && !header.isEmpty()) {
            try {
                AuditContextHolder.setProgramId(Long.parseLong(header));
            } catch (NumberFormatException e) {
                AuditContextHolder.setProgramId(0L); // 기본값 처리
            }
        }

        return true;
    }

    // 요청 처리가 완료된 후에 AuditContextHolder에서 클라이언트 IP와 프로그램 ID를 제거
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        AuditContextHolder.clearClientIp();
        AuditContextHolder.clearProgramId();
    }
}
