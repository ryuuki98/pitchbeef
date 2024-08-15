package com.pitchbeef.webservice.domain.user.util;


import com.pitchbeef.webservice.domain.user.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    // JWT 생성
    public String generateToken(String memberId) {
        return Jwts.builder()
                .claim("memberId", memberId) // "memberId"라는 클레임에 멤버 ID 저장
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()) // secret key as bytes
                .compact();
    }

    // JWT에서 Claims 추출
    public Claims getClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
              return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getBody();
    }


    // JWT에서 멤버 ID 추출
    public String getMemberId(String token) {
        return getClaims(token).get("memberId", String.class);
    }

    // 토큰 유효성 검사 및 만료 여부 확인
    public boolean validateToken(String token) {
        log.info("Validating token {}", token); // 여기까지 동작함
        try {
            Claims claims = getClaims(token);
            log.info("claims {}", claims);

            Date expiration = claims.getExpiration();
            boolean isExpired = expiration.before(new Date());
            log.info("Token expiration time: {}, Current time: {}, isExpired: {}", expiration, new Date(), isExpired);

            return !isExpired; // 만료되지 않았다면 true 반환
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false; // 예외 발생 시 유효하지 않음으로 간주
        }
    }



    public String resolveToken(HttpServletRequest request) {
        // 모든 쿠키를 배열로 가져옵니다.
        Cookie[] cookies = request.getCookies();

        // 쿠키가 존재하는지 확인합니다.
        if (cookies != null) {
            // 모든 쿠키를 순회하며 원하는 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                // "AUTH_TOKEN"이라는 이름의 쿠키를 찾습니다.
                if ("AUTH_TOKEN".equals(cookie.getName())) {
                    // 해당 쿠키의 값을 반환합니다.
                    return cookie.getValue();
                }
            }
        }
        // 쿠키가 없거나 원하는 쿠키가 없다면 null을 반환합니다.
        return null;
    }

    public Authentication getAuthentication(String token) {
        String memberId = getMemberId(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


}
