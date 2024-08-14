package com.pitchbeef.webservice.web.user.controller;

import com.pitchbeef.webservice.domain.user.service.AuthService;
import com.pitchbeef.webservice.domain.user.service.UserService;
import com.pitchbeef.webservice.web.user.dto.UserJoinDto;
import com.pitchbeef.webservice.web.user.dto.UserLoginDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "user/join";
    }

    @PostMapping("/joinProcess")
    public String joinProcess(@ModelAttribute UserJoinDto userJoinDto) {
        userService.saveUser(userJoinDto);
        log.info("userJoinDto: {}", userJoinDto);
        return "redirect:/user/join";
    }

    @PostMapping("/loginProcess")
    public String loginProcess(@ModelAttribute UserLoginDto userLoginDto, HttpServletResponse response, Model model) {
        log.info("userLoginDto: {}", userLoginDto);
        String token = authService.authenticate(userLoginDto);
        log.info("token: {}", token);
        if (token != null) {
            log.info("JWT Token: {}", token);

            // JWT 토큰을 쿠키에 저장
            Cookie cookie = new Cookie("AUTH_TOKEN", token);
            cookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 쿠키를 접근할 수 없도록 설정
            cookie.setSecure(true); // HTTPS를 사용하는 경우에만 쿠키를 전송하도록 설정 (필요시)
            cookie.setPath("/"); // 쿠키의 유효 범위를 설정 (전체 도메인)
            cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효 기간 (예: 1일)

            response.addCookie(cookie);

            return "user/success"; // 성공 페이지로 이동
        } else {
            return "redirect:/?error=true"; // 로그인 실패 시 다시 로그인 페이지로
        }
    }
}
