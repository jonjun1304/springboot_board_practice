package com.okdk.board.util;

import com.okdk.board.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;

    // JWT 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 사용자 이름
                .setIssuedAt(new Date()) // 현재 시간
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration())) // 만료 시간
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 검증
    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // 토큰에서 사용자 이름 추출
        } catch (JwtException e) {
            return null; // 유효하지 않은 토큰
        }
    }
}
