package com.pitchbeef.webservice.web.user.controller;


import com.pitchbeef.webservice.domain.user.service.AuthService;
import com.pitchbeef.webservice.domain.user.service.UserService;
import com.pitchbeef.webservice.web.user.dto.UserJoinDto;
import com.pitchbeef.webservice.web.user.dto.UserLoginDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{username}")
    public ResponseEntity isDuplicatedUsername(@PathVariable String username) {
        log.info("isDuplicated username {}", username);
        if (userService.findByUsername(username) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(@RequestBody UserJoinDto userJoinDto) {
        log.info("joinProcess {}", userJoinDto);
        try {
            userService.saveUser(userJoinDto);
            log.info("userJoinDto: {}", userJoinDto);
            return ResponseEntity.ok().build(); // 성공적으로 처리된 경우 200 OK 응답
        } catch (Exception e) {
            log.error("Error processing user join", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred"); // 서버 오류 발생 시 500 응답
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        log.info("userLoginDto: {}", userLoginDto);
        String token = authService.authenticate(userLoginDto);
        log.info("token: {}", token);

        if (token != null) {
            log.info("JWT Token: {}", token);

            // JWT 토큰을 쿠키에 저장
            Cookie cookie = new Cookie("AUTH_TOKEN", token);
            cookie.setHttpOnly(true); // JavaScript에서 접근 불가
            cookie.setSecure(true); // HTTPS에서만 전송
            cookie.setPath("/"); // 전체 도메인에 대해 유효
            cookie.setMaxAge(60 * 60 * 24); // 1일 동안 유효

            response.addCookie(cookie);

            return ResponseEntity.ok().build(); // 성공 응답
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 혹은 비밀번호가 일치하지 않습니다."); // 실패 응답
        }
    }
}
