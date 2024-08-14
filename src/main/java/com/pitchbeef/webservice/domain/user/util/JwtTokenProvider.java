package com.pitchbeef.webservice.domain.user.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    // JWT 생성
    public String generateToken(String memberId) {
        return Jwts.builder()
                .claim("memberId", memberId) // "memberId"라는 클레임에 멤버 ID 저장
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secret key as bytes
                .compact();
    }

    // JWT에서 Claims 추출
    public Claims getClaims(String token) {
        JwtParser parser = (JwtParser) Jwts.parser() // Jwts.parser() 사용
                .setSigningKey(secretKey.getBytes()); // secret key as bytes
        return parser.parseClaimsJws(token).getBody();
    }

    // JWT에서 멤버 ID 추출
    public String getMemberId(String token) {
        return getClaims(token).get("memberId", String.class);
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
