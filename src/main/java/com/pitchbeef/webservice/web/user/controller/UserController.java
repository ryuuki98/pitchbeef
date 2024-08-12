package com.pitchbeef.webservice.web.user.controller;

import com.pitchbeef.webservice.domain.user.service.UserService;
import com.pitchbeef.webservice.web.user.dto.UserJoinDto;
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
}
