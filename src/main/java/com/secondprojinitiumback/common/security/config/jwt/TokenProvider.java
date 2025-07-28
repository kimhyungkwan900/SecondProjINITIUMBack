package com.secondprojinitiumback.common.security.config.jwt;

import com.secondprojinitiumback.common.login.constatnt.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    private static final String KEY_ID = "id";
    private static final String KEY_ROLE = "role";

    private static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(30);
    private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(30);

    @PostConstruct
    private void init(){
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getJwtSecret()));
    }

    // 엑세스토큰 생성
    public String generateAccessToken(Long userId, Role role){
        return generateToken(userId, role, ACCESS_TOKEN_DURATION);
    }

    // 리프래쉬토큰 생성
    public String generateRefreshToken(){
        return generateToken(null,null,REFRESH_TOKEN_DURATION);
    }

    // 토큰생성
    private String generateToken(Long userId, Role role, Duration duration){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + duration.toMillis());

        JwtBuilder builder = createBaseBuilder(now, expiryDate);

        if (userId != null){
            builder
                    .setSubject(userId.toString())
                    .claim(KEY_ID,userId);
        }
        if (role != null){
            builder.claim(KEY_ROLE,role);
        }

        return builder.signWith(getSecretKey(), SignatureAlgorithm.HS256).compact();
    }

    // 토큰의 Header, Issuer, 발급시간, 만료시간 설정
    private JwtBuilder createBaseBuilder(Date now, Date expiryDate) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getJwtIssuer())
                .setIssuedAt(now)
                .setExpiration(expiryDate);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 유효하지 않거나 만료된 경우
            return false;
        }
    }

    // 시크릿키 반환
    private SecretKey getSecretKey() {
        return secretKey;
    }

    // 토큰에서 사용자 ID 반환
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        if (claims == null) return null;
        Object idObj = claims.get(KEY_ID);
        if (idObj == null) return null;
        try {
            return Long.valueOf(idObj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // 토큰에서 사용자 Role 반환
    public Role getUserRole(String token){
        Claims claims = getClaims(token);
        if (claims == null) return null;
        Object roleObj = claims.get(KEY_ROLE);
        if (roleObj == null) return null;
        try {
            return Role.valueOf(roleObj.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // 토큰에서 Claims 반환
    public Claims getClaims(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
    // 토큰 생명주기 반환
    public int getAccessTokenExpirySeconds() {
        return (int) ACCESS_TOKEN_DURATION.getSeconds();
    }

    // refresh 토큰 생명주기 반환
    public int getRefreshTokenExpirySeconds() {
        return (int) REFRESH_TOKEN_DURATION.getSeconds();
    }

    public Duration getAccessTokenDuration() {
        return ACCESS_TOKEN_DURATION;
    }
}
