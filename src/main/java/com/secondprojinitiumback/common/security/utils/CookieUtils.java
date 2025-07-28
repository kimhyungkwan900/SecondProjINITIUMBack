package com.secondprojinitiumback.common.security.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import java.time.Duration;
import java.util.Base64;
import java.util.Optional;

public class CookieUtils {
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return Optional.empty();
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofSeconds(maxAge))
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void deleteCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        ResponseCookie expiredCookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // 즉시 만료
                .sameSite("Lax")
                .build();

        // 쿠키를 삭제하기 위해 응답 헤더에 추가
        HttpServletResponse response = (HttpServletResponse) request.getAttribute("response");
        if (response != null) {
            response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
        }
    }

    public static String serialize(Object obj){
        try{
            return Base64.getUrlEncoder()
                    .encodeToString(SerializationUtils.serialize(obj));
        } catch (Exception e) {
            throw new IllegalArgumentException("직렬화 실패", e);
        }
    }

    public static <T> T deserialize(Cookie cookie, Class<T> clazz){
        try {
            return clazz.cast(SerializationUtils.deserialize(
                    Base64.getUrlDecoder().decode(cookie.getValue())));
        } catch (Exception e) {
            throw new IllegalArgumentException("역직렬화 실패", e);
        }
    }
}
