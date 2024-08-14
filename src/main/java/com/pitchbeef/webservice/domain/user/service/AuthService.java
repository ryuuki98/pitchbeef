package com.pitchbeef.webservice.domain.user.service;


import com.pitchbeef.webservice.domain.user.model.User;
import com.pitchbeef.webservice.domain.user.repository.UserRepository;
import com.pitchbeef.webservice.domain.user.util.JwtTokenProvider;
import com.pitchbeef.webservice.web.user.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 사용자 인증 및 JWT 토큰 생성
     * @param userLoginDto
     * @return JWT 토큰
     */
    public String authenticate(UserLoginDto userLoginDto) {
        log.info("Authenticating user: {}", userLoginDto);
        User user = userRepository.findByUsername(userLoginDto.getUsername());
        if (user != null && passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            log.info("authenticated success: {}", user);
            // 인증 성공 시 JWT 토큰 생성

            return jwtTokenProvider.generateToken(String.valueOf(user.getMemberId()));
        }
        return null;
    }
}