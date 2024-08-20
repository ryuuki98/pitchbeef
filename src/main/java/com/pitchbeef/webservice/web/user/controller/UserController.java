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

    @GetMapping("/login")
    public String login(Model model) {
        log.info("login");
        model.addAttribute("user", new UserLoginDto());
        return "user/login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        log.info("join");
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "user/join";
    }

    @GetMapping("/success")
    public String loginSuccess() {
        log.info("loginSuccess");
        return "redirect:/";
    }
}
